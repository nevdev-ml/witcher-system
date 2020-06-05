package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.models.UserViewModel;
import com.nevdev.witcher.services.AuthorityService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.exceptions.CustomErrorType;
import com.nevdev.witcher.services.jwt.JwtAuthenticationRequest;
import com.nevdev.witcher.services.jwt.JwtAuthenticationResponse;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import com.nevdev.witcher.services.jwt.JwtUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("account")
public class AccountController {
    @Value("${jwt.header}")
    private String tokenHeader;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserDetailsService userDetailsService;

    private static final
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;

    private final AuthorityService authorityService;

    public AccountController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                             UserDetailsService userDetailsService, UserService userService,
                             AuthorityService authorityService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserViewModel user) {
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
            case "Ведьмак":
                user.setRole(Role.WITCHER);
                break;
            default:
                user.setRole(Role.USER);
                break;
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis()));
        user.setEnabled(true);

        Authority userAuthority = authorityService.find(user.getRole());
        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(userAuthority);
        user.setAuthorities(authoritiesList);
        return new ResponseEntity<>(userService.create(user.getUser()), HttpStatus.CREATED);
    }


    @GetMapping(value = "/profile")
    public User profile(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        Role role = Role.valueOf(((GrantedAuthority) ((ArrayList) user.getAuthorities()).get(0)).getAuthority());
        return new User(user.getUsername(), role, user.getFirstName(), user.getLastName(), user.getEmail());
    }


    private void addBuiltInUser(String name, String password, Role authorityName){
        User user = new User();
        user.setUsername(name);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setEmail(String.format("%1$s@%1$s.com", name));
        user.setFirstName(name.substring(0, 1).toUpperCase()+name.substring(1));
        user.setLastName(name.substring(0, 1).toUpperCase()+name.substring(1) + "ov");
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis()));

        Authority BLACKSMITH = new Authority();
        BLACKSMITH.setRoleName(Role.BLACKSMITH);
        Authority VENDOR = new Authority();
        VENDOR.setRoleName(Role.VENDOR);
        Authority USER = new Authority();
        USER.setRoleName(Role.USER);
        Authority KING = new Authority();
        KING.setRoleName(Role.KING);
        Authority WITCHER = new Authority();
        WITCHER.setRoleName(Role.WITCHER);
        authorityService.create(BLACKSMITH);
        authorityService.create(VENDOR);
        authorityService.create(USER);
        authorityService.create(KING);
        authorityService.create(WITCHER);

        Authority userAuthority = authorityService.find(authorityName);


        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(userAuthority);

        user.setAuthorities(authoritiesList);
        user.setRole(userAuthority.getRoleName());
        userService.create(user);

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       Device device) throws AuthenticationException {
        if (device.isMobile()) {
            logger.info("Hello mobile user!");
        } else if (device.isTablet()) {
            logger.info("Hello tablet user!");
        } else {
            logger.info("Hello desktop user!");
        }
        // addBuiltInUser(authenticationRequest.getUsername(), authenticationRequest.getPassword(), Role.USER);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        ArrayList<Authority> authorities = new ArrayList<>(userService.find(authenticationRequest.getUsername()).getAuthorities());
        List<String> authoritiesString = new ArrayList<>();
        authorities.forEach(authority -> authoritiesString.add(authority.getRoleName().toString()));

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, authoritiesString));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping ("/logout")
    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
