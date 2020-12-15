package com.nevdev.witcher.services;

import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.repository.TaskRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskService taskServiceUnderTest;

    @Before
    public void setUp() {
        taskServiceUnderTest = new TaskService();
        taskServiceUnderTest.taskRepository = mock(TaskRepository.class);
        taskServiceUnderTest.beastService = mock(BeastService.class);
        taskServiceUnderTest.locationService = mock(LocationService.class);
        taskServiceUnderTest.bankService = mock(BankService.class);
        taskServiceUnderTest.depositService = mock(DepositService.class);
    }

    @Test
    public void testFind1() {
        // Setup
        final List<Task> expectedResult = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));

        // Configure TaskRepository.findByTitle(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
        when(taskServiceUnderTest.taskRepository.findByTitle("title")).thenReturn(tasks);

        // Run the test
        final List<Task> result = taskServiceUnderTest.find("title");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFind2() {
        // Setup
        final List<Task> expectedResult = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));

        // Configure TaskRepository.findByDone(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
        when(taskServiceUnderTest.taskRepository.findByDone(false)).thenReturn(tasks);

        // Run the test
        final List<Task> result = taskServiceUnderTest.find(false);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetActiveQuests() {
        // Setup
        final List<Task> expectedResult = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));

        // Configure TaskRepository.findByDone(...).
        Task task = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        task.setDone(false);
        User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false);
        user.setId(0L);
        task.setWitchers(Collections.singletonList(user));
        final List<Task> tasks = Collections.singletonList(task);
        when(taskServiceUnderTest.taskRepository.findByDone(false)).thenReturn(tasks);

        // Run the test
        final List<Task> result = taskServiceUnderTest.getActiveQuests(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetWinHistoryQuests() {
        // Setup
        final List<Task> expectedResult = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));

        // Configure TaskRepository.findByWitcherIdAndDone(...).
        Task task = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        task.setDone(false);
        User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false);
        user.setId(0L);
        task.setWitchers(Collections.singletonList(user));
        final List<Task> tasks = Collections.singletonList(task);
        when(taskServiceUnderTest.taskRepository.findByWitcherIdAndDone(0L, true)).thenReturn(tasks);

        // Run the test
        final List<Task> result = taskServiceUnderTest.getWinHistoryQuests(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetLoseHistoryQuests() {
        // Setup
        final List<Task> expectedResult = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));

        // Configure TaskRepository.findByDone(...).
        Task task = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        task.setDone(false);
        task.setWitcherId(1L);
        User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false);
        user.setId(0L);
        task.setWitchers(Collections.singletonList(user));
        final List<Task> tasks = Collections.singletonList(task);
        when(taskServiceUnderTest.taskRepository.findByDone(true)).thenReturn(tasks);

        // Run the test
        final List<Task> result = taskServiceUnderTest.getLoseHistoryQuests(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetCustomerQuests() {
        // Setup
        final List<Task> expectedResult = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));

        // Configure TaskRepository.findByCustomerId(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
        when(taskServiceUnderTest.taskRepository.findByCustomerId(0L)).thenReturn(tasks);

        // Run the test
        final List<Task> result = taskServiceUnderTest.getCustomerQuests(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testRewardQuest() {
        // Setup
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        authority.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User customer = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User witcher = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        final Reward reward = new Reward(0.0, Currency.OREN);
        when(taskServiceUnderTest.bankService.getDeposits(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))), Currency.OREN)).thenReturn(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Configure BankService.find(...).
        final List<Bank> banks = Collections.singletonList(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))));
        when(taskServiceUnderTest.bankService.find(false)).thenReturn(banks);

        when(taskServiceUnderTest.depositService.edit(new Deposit(Currency.OREN, 0.0))).thenReturn(new Deposit(Currency.OREN, 0.0));

        // Run the test
        taskServiceUnderTest.rewardQuest(customer, witcher, reward);

        // Verify the results
    }

    @Test
    public void testCreate() {
        // Setup
        final Task model = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        final Task expectedResult = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(taskServiceUnderTest.beastService.create(new Beast(Bestiary.ALP))).thenReturn(new Beast(Bestiary.ALP));
        when(taskServiceUnderTest.locationService.create(new Location(Region.AEDIRN))).thenReturn(new Location(Region.AEDIRN));

        // Configure TaskRepository.save(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(taskServiceUnderTest.taskRepository.save(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task);

        // Run the test
        final Task result = taskServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Task model = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));

        // Run the test
        taskServiceUnderTest.delete(model);

        // Verify the results
        verify(taskServiceUnderTest.taskRepository).delete(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
        verify(taskServiceUnderTest.taskRepository).flush();
    }

    @Test
    public void testEdit() {
        // Setup
        final Task model = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        final Task expectedResult = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(taskServiceUnderTest.beastService.create(new Beast(Bestiary.ALP))).thenReturn(new Beast(Bestiary.ALP));
        when(taskServiceUnderTest.locationService.create(new Location(Region.AEDIRN))).thenReturn(new Location(Region.AEDIRN));

        // Configure TaskRepository.save(...).
        final Task task = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));
        when(taskServiceUnderTest.taskRepository.save(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))))).thenReturn(task);

        // Run the test
        final Task result = taskServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Task expectedResult = new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP)));

        // Configure TaskRepository.findById(...).
        final Optional<Task> task = Optional.of(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
        when(taskServiceUnderTest.taskRepository.findById(0L)).thenReturn(task);

        // Run the test
        final Task result = taskServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_TaskRepositoryReturnsAbsent() {
        // Setup
        when(taskServiceUnderTest.taskRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Task result = taskServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup

        // Configure TaskRepository.findAll(...).
        final List<Task> tasks = Collections.singletonList(new Task("title", "locationComment", new Location(Region.AEDIRN), new Reward(0.0, Currency.OREN), 0L, Collections.singletonList(new Beast(Bestiary.ALP))));
        when(taskServiceUnderTest.taskRepository.findAll()).thenReturn(tasks);

        // Run the test
        final Iterable<Task> result = taskServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(tasks);
    }
}
