package dev.dharam.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.dharam.userservice.entities.Role;
import dev.dharam.userservice.entities.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
@NoArgsConstructor
public class CustomUserDetail implements UserDetails {
//    private User user;
    private List<GrantedAuthority> authorities;
    private String password;
    private String username;
    private Long userId;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public CustomUserDetail(User user) {
//        this.user = user;
        authorities= new ArrayList<>();
        for(Role role: user.getRoles()){
            authorities.add(new CustomGrantedAuthority(role));
        }
        this.password = user.getPassword();
        this.username = user.getEmail();
        this.userId = user.getId();



    }

    public Long getUserId() {
        return this.userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
