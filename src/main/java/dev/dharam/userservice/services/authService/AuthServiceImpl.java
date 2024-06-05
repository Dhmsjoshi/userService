package dev.dharam.userservice.services.authService;

import dev.dharam.userservice.dtos.*;
import dev.dharam.userservice.entities.Role;
import dev.dharam.userservice.entities.Session;
import dev.dharam.userservice.entities.SessionStatus;
import dev.dharam.userservice.entities.User;
import dev.dharam.userservice.exceptions.UserAlreadyExistsException;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import dev.dharam.userservice.repositories.RoleRepository;
import dev.dharam.userservice.repositories.SessionRepository;
import dev.dharam.userservice.repositories.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;




    @Override
    public UserDto signUp(SignUpRequestDto request) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("User with email: "+request.getEmail() +" already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("USER").orElseThrow(
                ()->new RuntimeException("Unknown error")
        );

        roles.add(role);
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    @Override
    public ResponseEntity<UserDto> login(LoginRequestDto request) throws UserDoesNotExistsException {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new UserDoesNotExistsException("User with email: "+request.getEmail()+" does not exist!")
        );

        if(!passwordEncoder.matches( request.getPassword(),user.getPassword())){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        String token = RandomStringUtils.randomAscii(20);
        MultiValueMapAdapter<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", token);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);


        UserDto userDto = UserDto.from(user);
        ResponseEntity<UserDto> response = new ResponseEntity<>(
                userDto,
                headers,
                HttpStatus.OK
        );
        return response;
    }

    @Override
    public Optional<UserDto> validate(ValidateTokenRequestDto request) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(request.getToken(), request.getUserId());

        if(sessionOptional.isEmpty()){
            return Optional.empty();
        }

        Session session = sessionOptional.get();

        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return Optional.empty();
        }

//        if(Instant.now().isBefore(session.getExpiringAt())){
//            return SessionStatus.EXPIRED;
//        }

        User user = userRepository.findById(request.getUserId()).get();
        UserDto userDto = UserDto.from(user);

        return Optional.of(userDto);

    }


    @Override
    public ResponseEntity<Void> logout(LogoutRequestDto request) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(request.getToken(), request.getUserId());
        if(sessionOptional.isEmpty()){
            return null;
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }
}
