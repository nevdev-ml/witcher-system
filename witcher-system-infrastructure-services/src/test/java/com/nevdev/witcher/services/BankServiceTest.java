 package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Bank;
import com.nevdev.witcher.core.Deposit;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.BankRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BankServiceTest {

    @Mock
    private BankRepository mockBankRepository;

    @InjectMocks
    private BankService bankServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testFind() {
        // Setup
        final List<Bank> expectedResult = Collections.singletonList(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))));

        // Configure BankRepository.findByKingRepository(...).
        final List<Bank> banks = Collections.singletonList(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))));
        when(mockBankRepository.findByKingRepository(false)).thenReturn(banks);

        // Run the test
        final List<Bank> result = bankServiceUnderTest.find(false);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGetDeposits() {
        // Setup
        final Bank bank = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));
        final List<Deposit> expectedResult = Collections.singletonList(new Deposit(Currency.OREN, 0.0));

        // Run the test
        final List<Deposit> result = bankServiceUnderTest.getDeposits(bank, Currency.OREN);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Bank model = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));
        final Bank expectedResult = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Configure BankRepository.saveAndFlush(...).
        final Bank bank = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));
        when(mockBankRepository.saveAndFlush(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))))).thenReturn(bank);

        // Run the test
        final Bank result = bankServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Bank model = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Run the test
        bankServiceUnderTest.delete(model);

        // Verify the results
        verify(mockBankRepository).delete(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))));
    }

    @Test
    public void testEdit() {
        // Setup
        final Bank model = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));
        final Bank expectedResult = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Configure BankRepository.saveAndFlush(...).
        final Bank bank = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));
        when(mockBankRepository.saveAndFlush(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))))).thenReturn(bank);

        // Run the test
        final Bank result = bankServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Bank expectedResult = new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0)));

        // Configure BankRepository.findById(...).
        final Optional<Bank> bank = Optional.of(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))));
        when(mockBankRepository.findById(0L)).thenReturn(bank);

        // Run the test
        final Bank result = bankServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_BankRepositoryReturnsAbsent() {
        // Setup
        when(mockBankRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Bank result = bankServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup

        // Configure BankRepository.findAll(...).
        final List<Bank> banks = Collections.singletonList(new Bank(Collections.singletonList(new Deposit(Currency.OREN, 0.0))));
        when(mockBankRepository.findAll()).thenReturn(banks);

        // Run the test
        final Iterable<Bank> result = bankServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(banks);
    }
}
