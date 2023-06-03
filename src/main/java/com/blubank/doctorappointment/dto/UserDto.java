package com.blubank.doctorappointment.dto;

import com.blubank.doctorappointment.utils.EnumNamePattern;
import com.blubank.doctorappointment.utils.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
public class UserDto implements UserDetails {
    private Long userId;
    @Size(max = 100)
    private String username;
    @Size(max = 200)
    private String password;
    private String confirmPassword;
    @EnumNamePattern(regexp = "DOCTOR|CLIENT")
    private Roles role;
    @Pattern(regexp="(^$|[0-9]{11})")
    private String mobile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
