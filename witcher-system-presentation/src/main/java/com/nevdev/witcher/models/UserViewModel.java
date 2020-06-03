package com.nevdev.witcher.models;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.Role;
import com.nevdev.witcher.core.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserViewModel extends User {
    private String checkedRole;

    public UserViewModel(String username, String password, Role role, String firstName, String lastName, String email,
                         String checkedRole, Boolean enabled, Date lastPasswordResetDate, List<Authority> authorities) {
        super(username, password, role, firstName, lastName, email, enabled, lastPasswordResetDate, authorities);
        this.checkedRole = checkedRole;
    }

    public User getUser(){
        return new User(this.getUsername(), this.getPassword(), this.getRole(), this.getFirstName(), this.getLastName(),
                this.getEmail(), this.getEnabled(), this.getLastPasswordResetDate(), this.getAuthorities());
    }
}
