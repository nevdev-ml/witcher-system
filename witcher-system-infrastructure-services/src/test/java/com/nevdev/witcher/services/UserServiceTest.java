package com.nevdev.witcher.services;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userServiceUnderTest;

    @Before
    public void setUp() {
        userServiceUnderTest = new UserService();
        userServiceUnderTest.userRepository = mock(UserRepository.class);
        userServiceUnderTest.taskService = mock(TaskService.class);
        userServiceUnderTest.dealService = mock(DealService.class);
    }

    @Test
    public void testFind() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);

        // Configure UserRepository.findByUsername(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(userServiceUnderTest.userRepository.findByUsername("value")).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.find("value");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByUsernameAndPassword() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);

        // Configure UserRepository.findByUsernameAndPassword(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(userServiceUnderTest.userRepository.findByUsernameAndPassword("username", "password")).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.findByUsernameAndPassword("username", "password");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindByUsernameOrEmail() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);

        // Configure UserRepository.findByUsernameOrEmail(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(userServiceUnderTest.userRepository.findByUsernameOrEmail("username", "email")).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.findByUsernameOrEmail("username", "email");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User model = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);

        // Configure UserRepository.findByUsername(...).
        final Authority authority2 = new Authority();
        authority2.setId(0L);
        authority2.setRoleName(Role.USER);
        authority2.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority2), false);
        when(userServiceUnderTest.userRepository.findByUsername("value")).thenReturn(user);

        // Configure UserRepository.saveAndFlush(...).
        final Authority authority3 = new Authority();
        authority3.setId(0L);
        authority3.setRoleName(Role.USER);
        authority3.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority3), false);
        when(userServiceUnderTest.userRepository.saveAndFlush(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user1);

        // Run the test
        final User result = userServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User model = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
//        model.setId(0L);
        // Configure TaskService.getAll(...).
        Task task = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
//        task.setCustomerId(0L);
        final Iterable<Task> tasks = Collections.singletonList(task);
        when(userServiceUnderTest.taskService.getAll()).thenReturn(tasks);

        // Configure DealService.getAll(...).
        Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
//        deal.setCustomerId(0L);
        final Iterable<Deal> deals = Collections.singletonList(deal);
        when(userServiceUnderTest.dealService.getAll()).thenReturn(deals);

        // Run the test
        userServiceUnderTest.delete(model);

        // Verify the results
//        verify(userServiceUnderTest.taskService).delete(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
//        verify(userServiceUnderTest.dealService).delete(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        verify(userServiceUnderTest.taskService).getAll();
        verify(userServiceUnderTest.userRepository).delete(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false));
    }

    @Test
    public void testEdit() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User model = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);

        // Configure UserRepository.saveAndFlush(...).
        final Authority authority2 = new Authority();
        authority2.setId(0L);
        authority2.setRoleName(Role.USER);
        authority2.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority2), false);
        when(userServiceUnderTest.userRepository.saveAndFlush(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false))).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);

        // Configure UserRepository.findById(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final Optional<User> user = Optional.of(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false));
        when(userServiceUnderTest.userRepository.findById(0L)).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_UserRepositoryReturnsAbsent() {
        // Setup
        when(userServiceUnderTest.userRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final User result = userServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup

        // Configure UserRepository.findAll(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final List<User> users = Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false));
        when(userServiceUnderTest.userRepository.findAll()).thenReturn(users);

        // Run the test
        final Iterable<User> result = userServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(users);
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);

        // Configure UserRepository.findByEmail(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(userServiceUnderTest.userRepository.findByEmail("email")).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.findUserByEmail("email");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindUserByResetToken() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User expectedResult = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);

        // Configure UserRepository.findByResetToken(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(userServiceUnderTest.userRepository.findByResetToken("resetToken")).thenReturn(user);

        // Run the test
        final User result = userServiceUnderTest.findUserByResetToken("resetToken");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
