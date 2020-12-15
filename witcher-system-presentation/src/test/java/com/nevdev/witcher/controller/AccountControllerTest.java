package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.PasswordModifyModel;
import com.nevdev.witcher.models.UserViewModel;
import com.nevdev.witcher.services.AuthorityService;
import com.nevdev.witcher.services.EmailService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.jwt.JwtAuthenticationRequest;
import com.nevdev.witcher.services.jwt.JwtAuthenticationResponse;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import com.nevdev.witcher.services.jwt.JwtUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountControllerTest {

    @Mock
    private AuthenticationManager mockAuthenticationManager;
    @Mock
    private JwtTokenUtil mockJwtTokenUtil;
    @Mock
    private UserDetailsService mockUserDetailsService;
    @Mock
    private UserService mockUserService;
    @Mock
    private AuthorityService mockAuthorityService;
    @Mock
    private EmailService mockEmailService;

    private AccountController accountControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        accountControllerUnderTest = new AccountController(mockAuthenticationManager, mockJwtTokenUtil, mockUserDetailsService, mockUserService, mockAuthorityService, mockEmailService);
    }

    @Test
    public void testRegister() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final UserViewModel user = new UserViewModel("username", "password", Role.USER, "firstName", "lastName", "email", "checkedRole", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority));

        // Configure UserService.find(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.find("value")).thenReturn(user1);

        // Configure AuthorityService.create(...).
        final Authority authority2 = new Authority();
        authority2.setId(0L);
        authority2.setRoleName(Role.USER);
        authority2.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        when(mockAuthorityService.create(new Authority())).thenReturn(authority2);

        // Configure UserService.create(...).
        final Authority authority3 = new Authority();
        authority3.setId(0L);
        authority3.setRoleName(Role.USER);
        authority3.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user2 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority3), false);
        when(mockUserService.create(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user2);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.register(user);

        // Verify the results
        assertThat(result).isEqualTo(new ResponseEntity<>(user2, HttpStatus.CREATED));
    }

    @Test
    public void testLogin() {
        // Setup
        final JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest("username", "password");
        final Device device = new Device() {
            @Override
            public boolean isMobile() {
                return true;
            }
        };
        final UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
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
        };

        // Configure UserService.find(...).
        String password = BCrypt.hashpw("password", BCrypt.gensalt());
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", password, Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", password, Role.USER, "firstName", "lastName", "email", true, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.find("username")).thenReturn(user);

        when(mockAuthenticationManager.authenticate(null)).thenReturn(null);
        when(mockUserDetailsService.loadUserByUsername("username")).thenReturn(userDetails);
        when(mockJwtTokenUtil.generateToken(any(UserDetails.class), any(Device.class))).thenReturn("result");

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.login(authenticationRequest, device);

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(ResponseEntity.ok(new JwtAuthenticationResponse("result", Collections.singletonList(authority), user.getId())).getStatusCode());
    }

    @Test
    public void testLogout() {
        // Setup

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.logout();

        // Verify the results
    }

    @Test
    public void testResetPassword() {
        // Setup

        // Configure UserService.findUserByResetToken(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.findUserByResetToken("resetToken")).thenReturn(user);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.resetPassword("token");

        // Verify the results
    }

    @Test
    public void testSetNewPassword() {
        // Setup

        // Configure UserService.findUserByResetToken(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.findUserByResetToken("resetToken")).thenReturn(user);

        // Configure UserService.edit(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.edit(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.setNewPassword("password", "token");

        // Verify the results
    }

    @Test
    public void testEdit() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final UserViewModel user = new UserViewModel("username", "password", Role.USER, "firstName", "lastName", "email", "checkedRole", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority));

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Configure AuthorityService.create(...).
        final Authority authority2 = new Authority();
        authority2.setId(0L);
        authority2.setRoleName(Role.USER);
        authority2.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        when(mockAuthorityService.create(new Authority())).thenReturn(authority2);

        // Configure UserService.edit(...).
        final Authority authority3 = new Authority();
        authority3.setId(0L);
        authority3.setRoleName(Role.USER);
        authority3.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user2 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority3), false);
        when(mockUserService.edit(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user2);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.edit(user);

        // Verify the results
    }

    @Test
    public void testEditPassword() {
        // Setup
        final PasswordModifyModel model = new PasswordModifyModel();
        model.setPassword("password");
        model.setUserId(0L);

        // Configure UserService.get(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.get(0L)).thenReturn(user);

        // Configure UserService.edit(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.edit(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.editPassword(model);

        // Verify the results
    }

    @Test
    public void testUsers() {
        // Setup
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.find("value")).thenReturn(user);

        // Configure UserService.getAll(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final Iterable<User> users = Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false));
        when(mockUserService.getAll()).thenReturn(users);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.users("token");

        // Verify the results
    }

    @Test
    public void testEnable() {
        // Setup
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.find("value")).thenReturn(user);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Configure UserService.edit(...).
        final Authority authority2 = new Authority();
        authority2.setId(0L);
        authority2.setRoleName(Role.USER);
        authority2.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user2 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority2), false);
        when(mockUserService.edit(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user2);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.enable("token", 0L);

        // Verify the results
    }

    @Test
    public void testDisable() {
        // Setup
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.find("value")).thenReturn(user);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Configure UserService.edit(...).
        final Authority authority2 = new Authority();
        authority2.setId(0L);
        authority2.setRoleName(Role.USER);
        authority2.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user2 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority2), false);
        when(mockUserService.edit(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user2);

        // Run the test
        final ResponseEntity<?> result = accountControllerUnderTest.disable("token", 0L);

        // Verify the results
    }
}
