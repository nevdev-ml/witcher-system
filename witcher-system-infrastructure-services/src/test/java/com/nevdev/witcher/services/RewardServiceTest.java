package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.RewardRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RewardServiceTest {

    private RewardService rewardServiceUnderTest;

    @Before
    public void setUp() {
        rewardServiceUnderTest = new RewardService();
        rewardServiceUnderTest.rewardRepository = mock(RewardRepository.class);
    }

    @Test
    public void testFind() {
        // Setup
        final List<Reward> expectedResult = Collections.singletonList(new Reward(0.0, Currency.OREN));
        when(rewardServiceUnderTest.rewardRepository.findByType(Currency.OREN)).thenReturn(Collections.singletonList(new Reward(0.0, Currency.OREN)));

        // Run the test
        final List<Reward> result = rewardServiceUnderTest.find(Currency.OREN);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Reward model = new Reward(0.0, Currency.OREN);
        final Reward expectedResult = new Reward(0.0, Currency.OREN);
        when(rewardServiceUnderTest.rewardRepository.saveAndFlush(new Reward(0.0, Currency.OREN))).thenReturn(new Reward(0.0, Currency.OREN));

        // Run the test
        final Reward result = rewardServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Reward model = new Reward(0.0, Currency.OREN);

        // Run the test
        rewardServiceUnderTest.delete(model);

        // Verify the results
        verify(rewardServiceUnderTest.rewardRepository).delete(new Reward(0.0, Currency.OREN));
    }

    @Test
    public void testEdit() {
        // Setup
        final Reward model = new Reward(0.0, Currency.OREN);
        final Reward expectedResult = new Reward(0.0, Currency.OREN);
        when(rewardServiceUnderTest.rewardRepository.saveAndFlush(new Reward(0.0, Currency.OREN))).thenReturn(new Reward(0.0, Currency.OREN));

        // Run the test
        final Reward result = rewardServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Reward expectedResult = new Reward(0.0, Currency.OREN);
        when(rewardServiceUnderTest.rewardRepository.findById(0L)).thenReturn(Optional.of(new Reward(0.0, Currency.OREN)));

        // Run the test
        final Reward result = rewardServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_RewardRepositoryReturnsAbsent() {
        // Setup
        when(rewardServiceUnderTest.rewardRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Reward result = rewardServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup
        when(rewardServiceUnderTest.rewardRepository.findAll()).thenReturn(Collections.singletonList(new Reward(0.0, Currency.OREN)));

        // Run the test
        final Iterable<Reward> result = rewardServiceUnderTest.getAll();

        // Verify the results
    }
}
