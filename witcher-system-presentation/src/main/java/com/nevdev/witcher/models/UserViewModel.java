package com.nevdev.witcher.models;

import com.nevdev.witcher.core.Role;
import com.nevdev.witcher.core.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserViewModel extends User {
    private String checkedRole;

    public UserViewModel(String username, String password, Role role, String name, String surname, String midname,
                         String checkedRole) {
        super(username, password, role, name, surname, midname);
        this.checkedRole = checkedRole;
    }

    public User getUser(){
        return new User(this.getUsername(), this.getPassword(), this.getRole(), this.getName(), this.getSurname(),
                this.getMidname());
    }
}
