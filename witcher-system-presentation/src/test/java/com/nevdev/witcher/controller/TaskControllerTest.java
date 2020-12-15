package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.TaskModifyViewModel;
import com.nevdev.witcher.services.TaskService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TaskControllerTest {

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
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER,
                "firstName", "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);

        when(mockUserService.find("username")).thenReturn(user);

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

    @Test
    public void testQuests() {
        // Setup
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER,
                "firstName", "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.getActiveQuests(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))));
        when(mockTaskService.getActiveQuests(0L)).thenReturn(tasks);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username1", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(1L)).thenReturn(user1);

        // Configure TaskService.getWinHistoryQuests(...).
        final List<Task> tasks1 = Collections.singletonList(new Task("title1", "locationComment1",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))));
        when(mockTaskService.getWinHistoryQuests(0L)).thenReturn(tasks1);

        // Configure TaskService.getLoseHistoryQuests(...).
        final List<Task> tasks2 = Collections.singletonList(new Task("title1", "locationComment2",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))));
        when(mockTaskService.getLoseHistoryQuests(0L)).thenReturn(tasks2);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.quests("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockUserService, times(3)).get(anyLong());
        verify(mockTaskService).getActiveQuests(anyLong());
        verify(mockTaskService).getWinHistoryQuests(anyLong());
        verify(mockTaskService).getLoseHistoryQuests(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testCustomerQuests() {
        // Setup
        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.getCustomerQuests(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))));
        when(mockTaskService.getCustomerQuests(0L)).thenReturn(tasks);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.customerQuests("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockTaskService).getCustomerQuests(anyLong());
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testAdd() {
        // Setup
        final TaskModifyViewModel taskRequest = new TaskModifyViewModel();
        taskRequest.setCheckedLocation("checkedLocation");
        taskRequest.setCheckedCurrency("checkedCurrency");
        taskRequest.setCheckedRewardValue(0.0);
        taskRequest.setCheckedBeast("checkedBeast");
        taskRequest.setTitle("title");
        taskRequest.setLocationComment("locationComment");

        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.create(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.create(new Task("title", "locationComment",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task);
          // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.add(taskRequest, "token");

        // Verify the results
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testDetails() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        task.setId(0L);
        when(mockTaskService.get(0L)).thenReturn(task);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.details(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testAccept() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.get(0L)).thenReturn(task);

        // Configure TaskService.edit(...).
        final Task task1 = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.edit(new Task("title", "locationComment",
                new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task1);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.accept(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verify(mockTaskService).edit(any(Task.class));
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testCancel() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L,
                Collections.singletonList(new Beast(Bestiary.ALP)));
        task.setId(0L);
        when(mockTaskService.get(0L)).thenReturn(task);

        // Configure TaskService.edit(...).
        when(mockTaskService.edit(task)).thenReturn(task);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.cancel(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verify(mockUserService, times(1)).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testComplete() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.get(0L)).thenReturn(task);

        // Configure TaskService.edit(...).
        final Task task1 = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.edit(new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.complete(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verify(mockTaskService).edit(any(Task.class));
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testReward() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.get(0L)).thenReturn(task);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        doNothing().when(mockTaskService).rewardQuest(user, user1, new Reward(0.0, Currency.OREN));

        // Configure TaskService.edit(...).
        final Task task1 = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.edit(new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.reward(request, 0L, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());

        verify(mockUserService).get(anyLong());
        verify(mockTaskService).edit(any(Task.class));
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testRefuse() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.get(0L)).thenReturn(task);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Configure TaskService.edit(...).
        final Task task1 = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.edit(new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task1);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.refuse(request, 0L, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verify(mockUserService).get(anyLong());
        verify(mockTaskService).edit(any(Task.class));
        verifyNoMoreInteractions(mockJwtTokenUtil);
        verifyNoMoreInteractions(mockTaskService);
    }

    @Test
    public void testDelete() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("username");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.KING);
        final User user = new User("username", "password", Role.KING, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);

        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        task.setId(0L);
        when(mockTaskService.get(0L)).thenReturn(task);

        doNothing().when(mockTaskService).delete(task);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.delete(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testEdit() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        final TaskModifyViewModel task = new TaskModifyViewModel();
        task.setCheckedLocation("checkedLocation");
        task.setCheckedCurrency("checkedCurrency");
        task.setCheckedRewardValue(0.0);
        task.setCheckedBeast("checkedBeast");
        task.setTitle("title");
        task.setLocationComment("locationComment");

        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure TaskService.get(...).
        final Task task1 = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        task1.setId(0L);
        when(mockTaskService.get(0L)).thenReturn(task1);
        when(mockTaskService.edit(task1)).thenReturn(task1);
        // Configure TaskService.edit(...).
        final Task task2 = new Task("title", "locationComment", new Location(Region.AEDIRN),
                new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(mockTaskService.edit(task2)).thenReturn(task2);

        // Run the test
        final ResponseEntity<?> result = taskControllerUnderTest.edit(request, task, 0L);

        // Verify the results
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockTaskService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }
}
