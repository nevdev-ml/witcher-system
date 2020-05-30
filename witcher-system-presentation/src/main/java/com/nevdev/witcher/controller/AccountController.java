package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.Role;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.models.UserViewModel;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.exceptions.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("account")
public class AccountController {

    private static final
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserViewModel user) {
        if (userService.find(user.getUsername()) != null) {
            logger.error(String.format("Username already exist %s", user.getUsername()));
            return new ResponseEntity<>(new CustomErrorType(String.format("User with username %s already exist",
                    user.getUsername())), HttpStatus.CONFLICT);
        }
        switch (user.getCheckedRole()){
            case "Торговец":
                user.setRole(Role.BLACKSMITH);
                break;
            case "Ремесленник":
                user.setRole(Role.VENDOR);
                break;
            case "Король":
                user.setRole(Role.KING);
                break;
            default:
                user.setRole(Role.USER);
                break;
        }
        return new ResponseEntity<>(userService.create(user.getUser()), HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping("/login")
    public Principal user(Principal principal) {
        logger.info(String.format("User logged %s", principal));
        return principal;
    }
}
