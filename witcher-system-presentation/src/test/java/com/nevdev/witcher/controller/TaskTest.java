package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.services.TaskService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskTest {
    @Mock
    private JwtTokenUtil mockJwtTokenUtil;
    @Mock
    private TaskService mockTaskService;
    @Mock
    private UserService mockUserService;

    private TaskController taskControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        taskControllerUnderTest = new TaskController(mockJwtTokenUtil, mockTaskService, mockUserService);
    }

    @Test
    public void testTasks() {
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER,
                "firstName", "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);

        when(mockUserService.find("result")).thenReturn(user);

        // Configure TaskService.find(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))));
        when(mockTaskService.find(false)).thenReturn(tasks);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(1L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER,
                "firstName", "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER,
                "firstName", "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(1L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.tasks("token");

        // Verify the results
        assertThat(result.getBody()).isEqualTo(tasks);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockTaskService).find(anyBoolean());
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }
}
