package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Deposit;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.DepositRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DepositServiceTest {

    private DepositService depositServiceUnderTest;

    @Before
    public void setUp() {
        depositServiceUnderTest = new DepositService();
        depositServiceUnderTest.depositRepository = mock(DepositRepository.class);
    }

    @Test
    public void testFind() {
        // Setup
        final List<Deposit> expectedResult = Collections.singletonList(new Deposit(Currency.OREN, 0.0));
        when(depositServiceUnderTest.depositRepository.findByType(Currency.OREN)).thenReturn(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Run the test
        final List<Deposit> result = depositServiceUnderTest.find(Currency.OREN);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Deposit model = new Deposit(Currency.OREN, 0.0);
        final Deposit expectedResult = new Deposit(Currency.OREN, 0.0);
        when(depositServiceUnderTest.depositRepository.saveAndFlush(new Deposit(Currency.OREN, 0.0))).thenReturn(new Deposit(Currency.OREN, 0.0));

        // Run the test
        final Deposit result = depositServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Deposit model = new Deposit(Currency.OREN, 0.0);

        // Run the test
        depositServiceUnderTest.delete(model);

        // Verify the results
        verify(depositServiceUnderTest.depositRepository).delete(new Deposit(Currency.OREN, 0.0));
    }

    @Test
    public void testEdit() {
        // Setup
        final Deposit model = new Deposit(Currency.OREN, 0.0);
        final Deposit expectedResult = new Deposit(Currency.OREN, 0.0);
        when(depositServiceUnderTest.depositRepository.saveAndFlush(new Deposit(Currency.OREN, 0.0))).thenReturn(new Deposit(Currency.OREN, 0.0));

        // Run the test
        final Deposit result = depositServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Deposit expectedResult = new Deposit(Currency.OREN, 0.0);
        when(depositServiceUnderTest.depositRepository.findById(0L)).thenReturn(Optional.of(new Deposit(Currency.OREN, 0.0)));

        // Run the test
        final Deposit result = depositServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_DepositRepositoryReturnsAbsent() {
        // Setup
        when(depositServiceUnderTest.depositRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Deposit result = depositServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup
        when(depositServiceUnderTest.depositRepository.findAll()).thenReturn(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Run the test
        final Iterable<Deposit> result = depositServiceUnderTest.getAll();

        // Verify the results
    }
}
