package dev.dharam.userservice.services.authService;

import dev.dharam.userservice.dtos.*;
import dev.dharam.userservice.entities.Session;
import dev.dharam.userservice.entities.SessionStatus;
import dev.dharam.userservice.entities.User;
import dev.dharam.userservice.exceptions.UserAlreadyExistsException;
import dev.dharam.userservice.exceptions.UserDoesNotExistsException;
import dev.dharam.userservice.repositories.SessionRepository;
import dev.dharam.userservice.repositories.UserRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SessionRepository sessionRepository;

    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDto signUp(SignUpRequestDto request) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("User with email: "+request.getEmail() +" already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }

    @Override
    public ResponseEntity<UserDto> login(LoginRequestDto request) throws UserDoesNotExistsException {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new UserDoesNotExistsException("User with email: "+request.getEmail()+" does not exist!")
        );

        if(!bCryptPasswordEncoder.matches( request.getPassword(),user.getPassword())){
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
    public SessionStatus validate(ValidateTokenRequestDto request) {
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(request.getToken(), request.getUserId());

        if(sessionOptional.isEmpty()){
            return SessionStatus.INVALID;
        }

        Session session = sessionOptional.get();

        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.EXPIRED;
        }

//        if(Instant.now().isBefore(session.getExpiringAt())){
//            return SessionStatus.EXPIRED;
//        }
        return SessionStatus.ACTIVE;

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
