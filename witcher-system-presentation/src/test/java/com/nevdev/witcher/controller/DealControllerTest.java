package com.nevdev.witcher.controller;

import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.Deal;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.models.DealModifyViewModel;
import com.nevdev.witcher.services.DealService;
import com.nevdev.witcher.services.UserService;
import com.nevdev.witcher.services.jwt.JwtTokenUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DealControllerTest {

    @Mock
    private JwtTokenUtil mockJwtTokenUtil;
    @Mock
    private DealService mockDealService;
    @Mock
    private UserService mockUserService;

    private DealController dealControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        dealControllerUnderTest = new DealController(mockJwtTokenUtil, mockDealService, mockUserService);
    }

    @Test
    public void testDeals() {
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.findDone(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.findDone(false)).thenReturn(deals);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.deals("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).findDone(anyBoolean());
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testShop() {
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.findTraderAndDone(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.findTraderAndDone(false, false)).thenReturn(deals);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        user1.setId(1L);
        when(mockUserService.get(1L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.shop("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).findTraderAndDone(anyBoolean(), anyBoolean());
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testWorkshop() {
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.findTraderAndDone(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.findTraderAndDone(false, false)).thenReturn(deals);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.workshop("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).findTraderAndDone(anyBoolean(), anyBoolean());
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testAcceptedDeals() {
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.getActiveDeals(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.getActiveDeals(0L)).thenReturn(deals);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Configure DealService.getSuccessHistoryDeals(...).
        final List<Deal> deals1 = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.getSuccessHistoryDeals(0L)).thenReturn(deals1);

        // Configure DealService.getBookmarkedHistoryDeals(...).
        final List<Deal> deals2 = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.getBookmarkedHistoryDeals(0L)).thenReturn(deals2);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.acceptedDeals("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).getActiveDeals(anyLong());
        verify(mockDealService).getSuccessHistoryDeals(anyLong());
        verify(mockDealService).getBookmarkedHistoryDeals(anyLong());
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockUserService, times(3)).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testCustomerDeals() {
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.getCustomerDeals(...).
        final List<Deal> deals = Collections.singletonList(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L));
        when(mockDealService.getCustomerDeals(0L)).thenReturn(deals);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.customerDeals("token");

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).getCustomerDeals(anyLong());
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testAdd() {
        // Setup
        final DealModifyViewModel dealRequest = new DealModifyViewModel();
        dealRequest.setCheckedType("checkedType");
        dealRequest.setCheckedCurrency("checkedCurrency");
        dealRequest.setCheckedRewardValue(0.0);
        dealRequest.setTitle("title");
        dealRequest.setDescription("description");

        when(mockJwtTokenUtil.getUsernameFromToken("token")).thenReturn("username1");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        user.setId(0L);
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.create(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.create(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.add(dealRequest, "token");

        // Verify the results
        verify(mockJwtTokenUtil).getUsernameFromToken(anyString());
        verify(mockUserService).find(anyString());
        verifyNoMoreInteractions(mockJwtTokenUtil);
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
        user.setId(0L);
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.details(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verify(mockDealService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Configure DealService.edit(...).
        final Deal deal1 = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.edit(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal1);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.accept(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).edit(any(Deal.class));
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verify(mockDealService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Configure DealService.edit(...).
        final Deal deal1 = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.edit(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal1);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.cancel(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).edit(any(Deal.class));
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verify(mockDealService).get(anyLong());
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        deal.setId(0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Configure DealService.edit(...).
        final Deal deal1 = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.edit(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.complete(request, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).edit(any(Deal.class));
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockDealService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false,
                new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        final User user1 = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority1), false);
        user1.setId(1L);
        authority1.setUsers(Collections.singletonList(user1));
        when(mockUserService.get(1L)).thenReturn(user1);
        doNothing().when(mockDealService).acceptDeal(user, user1, new Reward(0.0, Currency.OREN));
        // Configure DealService.edit(...).
        final Deal deal1 = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.edit(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.reward(request, 1L, 0L);

        // Verify the results
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockDealService).acceptDeal(any(User.class), any(User.class), any(Reward.class));
        verify(mockDealService).edit(any(Deal.class));
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verify(mockDealService).get(anyLong());
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
        final User user = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority), false);
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Configure UserService.get(...).
        final Authority authority1 = new Authority();
        authority1.setId(0L);
        authority1.setRoleName(Role.USER);
        authority1.setUsers(Collections.singletonList(new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(new Authority()), false)));
        final User user1 = new User("username", "password", Role.USER, "firstName", "lastName", "email", false, new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), Collections.singletonList(authority1), false);
        when(mockUserService.get(0L)).thenReturn(user1);

        // Configure DealService.edit(...).
        final Deal deal1 = new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.edit(new Deal("title", "description", false, false, new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal1);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.refuse(request, 0L, 0L);

        // Verify the
        verify(mockDealService).edit(any(Deal.class));
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockUserService).get(anyLong());
        verify(mockDealService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testDelete() {
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
        authority.setUsers(Collections.singletonList(user));
        when(mockUserService.find("username")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal = new Deal("title", "description", false, false,
                new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.delete(request, 0L);

        // Verify the results
        verify(mockDealService).delete(deal);
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockDealService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }

    @Test
    public void testEdit() {
        // Setup
        final HttpServletRequest request = new MockHttpServletRequest();
        final DealModifyViewModel deal = new DealModifyViewModel();
        deal.setCheckedType("checkedType");
        deal.setCheckedCurrency("checkedCurrency");
        deal.setCheckedRewardValue(0.0);
        deal.setTitle("title");
        deal.setDescription("description");

        when(mockJwtTokenUtil.getUsernameFromToken(null)).thenReturn("result");

        // Configure UserService.find(...).
        final Authority authority = new Authority();
        authority.setId(0L);
        authority.setRoleName(Role.USER);
        final User user = new User("username", "password", Role.USER, "firstName",
                "lastName", "email", false,
                new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(),
                Collections.singletonList(authority), false);
        when(mockUserService.find("value")).thenReturn(user);

        // Configure DealService.get(...).
        final Deal deal1 = new Deal("title", "description", false, false,
                new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.get(0L)).thenReturn(deal1);

        // Configure DealService.edit(...).
        final Deal deal2 = new Deal("title", "description", false, false,
                new Reward(0.0, Currency.OREN), 0L);
        when(mockDealService.edit(new Deal("title", "description", false, false,
                new Reward(0.0, Currency.OREN), 0L))).thenReturn(deal2);

        // Run the test
        final ResponseEntity<?> result = dealControllerUnderTest.edit(request, deal, 0L);

        // Verify the results
        verify(mockJwtTokenUtil).getUsernameFromToken(null);
        verify(mockUserService).find(anyString());
        verify(mockDealService).get(anyLong());
        verifyNoMoreInteractions(mockJwtTokenUtil);
    }
}
