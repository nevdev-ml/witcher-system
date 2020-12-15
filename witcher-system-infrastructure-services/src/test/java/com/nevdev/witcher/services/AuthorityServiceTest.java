package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.repository.AuthorityRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthorityServiceTest {

    private AuthorityService authorityServiceUnderTest;

    @Before
    public void setUp() {
        authorityServiceUnderTest = new AuthorityService();
        authorityServiceUnderTest.authorityRepository = mock(AuthorityRepository.class);
    }

    @Test
    public void testFind() {
        // Setup
        final Authority expectedResult = new Authority();
        expectedResult.setId(0L);
        expectedResult.setRoleName(Role.USER);
        expectedResult.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        // Configure AuthorityRepository.findByRoleName(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        when(authorityServiceUnderTest.authorityRepository.findByRoleName(Role.USER)).thenReturn(authority);

        // Run the test
        final Authority result = authorityServiceUnderTest.find(Role.USER);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Authority model = new Authority();
        model.setId(0L);
        model.setRoleName(Role.USER);
        model.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        final Authority expectedResult = new Authority();
        expectedResult.setId(0L);
        expectedResult.setRoleName(Role.USER);
        expectedResult.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        // Configure AuthorityRepository.findByRoleName(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        when(authorityServiceUnderTest.authorityRepository.findByRoleName(Role.USER)).thenReturn(authority);

        // Configure AuthorityRepository.saveAndFlush(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        when(authorityServiceUnderTest.authorityRepository.saveAndFlush(new Authority())).thenReturn(authority1);

        // Run the test
        final Authority result = authorityServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Authority model = new Authority();
        model.setId(0L);
        model.setRoleName(Role.USER);
        model.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        // Run the test
        authorityServiceUnderTest.delete(model);

        // Verify the results
        verify(authorityServiceUnderTest.authorityRepository).delete(model);
    }

    @Test
    public void testEdit() {
        // Setup
        final Authority model = new Authority();
        model.setId(0L);
        model.setRoleName(Role.USER);
        model.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        final Authority expectedResult = new Authority();
        expectedResult.setId(0L);
        expectedResult.setRoleName(Role.USER);
        expectedResult.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        // Configure AuthorityRepository.saveAndFlush(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        when(authorityServiceUnderTest.authorityRepository.saveAndFlush(authority)).thenReturn(authority);

        // Run the test
        final Authority result = authorityServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Authority expectedResult = new Authority();
        expectedResult.setId(0L);
        expectedResult.setRoleName(Role.USER);
        expectedResult.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));

        // Configure AuthorityRepository.findById(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final Optional<Authority> authority = Optional.of(authority1);
        when(authorityServiceUnderTest.authorityRepository.findById(0L)).thenReturn(authority);

        // Run the test
        final Authority result = authorityServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_AuthorityRepositoryReturnsAbsent() {
        // Setup
        when(authorityServiceUnderTest.authorityRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Authority result = authorityServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup

        // Configure AuthorityRepository.findAll(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final List<Authority> authorities = Collections.singletonList(authority);
        when(authorityServiceUnderTest.authorityRepository.findAll()).thenReturn(authorities);

        // Run the test
        final Iterable<Authority> result = authorityServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(authorities);
    }
}
