package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.Deal;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.repository.DealRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DealServiceTest {

    private DealService dealServiceUnderTest;

    @Before
    public void setUp() {
        dealServiceUnderTest = new DealService();
        dealServiceUnderTest.dealRepository = mock(DealRepository.class);
        dealServiceUnderTest.taskService = mock(TaskService.class);
    }

    @Test
    public void testFindDone() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByDone(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findByDone(false)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.findDone(false);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindPaid() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByPaid(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findByPaid(false)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.findPaid(false);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindSaleAndDone() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findBySaleAndDone(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findBySaleAndDone(false, false)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.findSaleAndDone(false, false);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testFindTraderAndDone() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByTraderAndDone(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findByTraderAndDone(false, false)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.findTraderAndDone(false, false);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetActiveDeals() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByDone(...).
        Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        deal.setDone(false);
        deal.setExecutorId(1L);
        User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false);
        user.setId(0L);
        user.setDealsBookmarked(Collections.singletonList(deal));
        deal.setExecutorsBookmarked(Collections.singletonList(user));
        final List<Deal> deals = Collections.singletonList(deal);
        when(dealServiceUnderTest.dealRepository.findByDone(false)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.getActiveDeals(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetSuccessHistoryDeals() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByExecutorIdAndDone(...).
        Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        final List<Deal> deals = Collections.singletonList(deal);
        when(dealServiceUnderTest.dealRepository.findByExecutorIdAndDone(0L, true)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.getSuccessHistoryDeals(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetBookmarkedHistoryDeals() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByDone(...).
        Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        deal.setDone(true);
        deal.setExecutorId(1L);
        User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false);
        user.setId(0L);
        user.setDealsBookmarked(Collections.singletonList(deal));
        deal.setExecutorsBookmarked(Collections.singletonList(user));
        final List<Deal> deals = Collections.singletonList(deal);
        when(dealServiceUnderTest.dealRepository.findByDone(true)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.getBookmarkedHistoryDeals(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetCustomerDeals() {
        // Setup
        final List<Deal> expectedResult = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));

        // Configure DealRepository.findByCustomerId(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findByCustomerId(0L)).thenReturn(deals);

        // Run the test
        final List<Deal> result = dealServiceUnderTest.getCustomerDeals(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testAcceptDeal() {
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
        final User executor = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        final Reward reward = new Reward(0.0, Currency.OREN);

        // Run the test
        dealServiceUnderTest.acceptDeal(customer, executor, reward);

        // Verify the results
        verify(dealServiceUnderTest.taskService).rewardQuest(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false), new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false), new Reward(0.0, Currency.OREN));
    }

    @Test
    public void testCreate() {
        // Setup
        final Deal model = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        final Deal expectedResult = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);

        // Configure DealRepository.findById(...).
        final Optional<Deal> deal = Optional.of(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findById(0L)).thenReturn(deal);

        // Configure DealRepository.saveAndFlush(...).
        final Deal deal1 = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(dealServiceUnderTest.dealRepository.saveAndFlush(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal1);

        // Run the test
        final Deal result = dealServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate_DealRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Deal model = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        final Deal expectedResult = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(dealServiceUnderTest.dealRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure DealRepository.saveAndFlush(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(dealServiceUnderTest.dealRepository.saveAndFlush(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal);

        // Run the test
        final Deal result = dealServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Deal model = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);

        // Run the test
        dealServiceUnderTest.delete(model);

        // Verify the results
        verify(dealServiceUnderTest.dealRepository).delete(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        verify(dealServiceUnderTest.dealRepository).flush();
    }

    @Test
    public void testEdit() {
        // Setup
        final Deal model = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        final Deal expectedResult = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);

        // Configure DealRepository.save(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(dealServiceUnderTest.dealRepository.save(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal);

        // Run the test
        final Deal result = dealServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Deal expectedResult = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);

        // Configure DealRepository.findById(...).
        final Optional<Deal> deal = Optional.of(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findById(0L)).thenReturn(deal);

        // Run the test
        final Deal result = dealServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_DealRepositoryReturnsAbsent() {
        // Setup
        when(dealServiceUnderTest.dealRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Deal result = dealServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup

        // Configure DealRepository.findAll(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(dealServiceUnderTest.dealRepository.findAll()).thenReturn(deals);

        // Run the test
        final Iterable<Deal> result = dealServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(deals);
    }
}
