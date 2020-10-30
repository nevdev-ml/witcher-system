package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.UserViewModel;
import com.nevdev.witcher.services.AuthorityService;
import com.nevdev.witcher.services.EmailService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


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

    private final EmailService emailService;

    private final AuthorityService authorityService;

    public AccountController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                             UserDetailsService userDetailsService, UserService userService,
                             AuthorityService authorityService, EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.authorityService = authorityService;
        this.emailService = emailService;
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

        Authority authority = new Authority();
        authority.setRoleName(user.getRole());
        Authority userAuthority = authorityService.create(authority);
        List<Authority> authoritiesList = new ArrayList<>();
        authoritiesList.add(userAuthority);
        user.setAuthorities(authoritiesList);
        return new ResponseEntity<>(userService.create(user.getUser()), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/profile")
    public User profile(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        Role role = Role.valueOf(((GrantedAuthority) ((ArrayList) user.getAuthorities()).get(0)).getAuthority());
        User dbUser = userService.findUserByEmail(user.getEmail());
        return new User(user.getUsername(), role, user.getFirstName(), user.getLastName(), user.getEmail(), dbUser.getBank());
    }


    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       Device device) throws AuthenticationException {
        if (authenticationRequest.getUsername() == null || authenticationRequest.getPassword() == null){
            logger.error("Failed to authenticate: Empty username or password");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (!new BCryptPasswordEncoder().matches(authenticationRequest.getPassword(),
                userService.find(authenticationRequest.getUsername()).getPassword())) {
            logger.error("Failed to authenticate: Wrong username or password");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (device.isMobile()) {
            logger.info("Hello mobile user!");
        } else if (device.isTablet()) {
            logger.info("Hello tablet user!");
        } else {
            logger.info("Hello desktop user!");
        }
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

        return ResponseEntity.ok(new JwtAuthenticationResponse(token, authoritiesString,
                userService.find(authenticationRequest.getUsername()).getId()));
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

    @RequestMapping ("/logout")
    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping (value = "/forgot/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> forgotPassword(@PathVariable String email, HttpServletRequest request){
        User user = userService.findUserByEmail(email);
        if (user == null) {
            logger.error(String.format("We didn't find an account for that e-mail address: %s", email));
            return new ResponseEntity<>(new CustomErrorType(String.format("We didn't find an account for that " +
                    "e-mail address: %s", email)), HttpStatus.CONFLICT);
        }
        user.setResetToken(UUID.randomUUID().toString());
        userService.edit(user);

        //TODO: Change
        String angular_port = ":4200";
        String url = request.getScheme() + "://" + request.getServerName() + angular_port;
        String subject = "Запрос на сброс пароля";
        String text = "Для сброса пароля, пожалуйста, перейдите по ссылке ниже:\n" + url + "/reset/" + user.getResetToken();
        emailService.sendEmail(email, subject, text);
        logger.info("A password reset link has been sent to " + email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Display form to reset password
    @RequestMapping(value = "/reset/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> resetPassword(@PathVariable String token) {
        User user = userService.findUserByResetToken(token);
        if (user == null) {
            logger.error("This is an invalid password reset link");
            return new ResponseEntity<>(new CustomErrorType("This is an invalid password reset link"),
                    HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/reset/{token}", method = RequestMethod.POST)
    public ResponseEntity<?> setNewPassword(@RequestBody String password, @PathVariable String token) {
        User user = userService.findUserByResetToken(token);
        if (user == null) {
            logger.error("This is an invalid password reset link");
            return new ResponseEntity<>(new CustomErrorType("This is an invalid password reset link"),
                    HttpStatus.CONFLICT);
        }
        logger.info(user.getPassword());
        logger.info(user.getResetToken());
        logger.info(password);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setLastPasswordResetDate(new Date(System.currentTimeMillis()));
        user.setResetToken(null);
        logger.info(user.getPassword());
        logger.info(user.getResetToken());
        return new ResponseEntity<>(userService.edit(user), HttpStatus.OK);
    }
}
