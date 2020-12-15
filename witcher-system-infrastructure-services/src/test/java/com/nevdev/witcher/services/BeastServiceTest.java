package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Beast;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.repository.BeastRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BeastServiceTest {

    private BeastService beastServiceUnderTest;

    @Before
    public void setUp() {
        beastServiceUnderTest = new BeastService();
        beastServiceUnderTest.beastRepository = mock(BeastRepository.class);
    }

    @Test
    public void testFind() {
        // Setup
        final Beast expectedResult = new Beast(Bestiary.ALP);
        when(beastServiceUnderTest.beastRepository.findByBeastName(Bestiary.ALP)).thenReturn(new Beast(Bestiary.ALP));

        // Run the test
        final Beast result = beastServiceUnderTest.find(Bestiary.ALP);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Beast model = new Beast(Bestiary.ALP);
        final Beast expectedResult = new Beast(Bestiary.ALP);
        when(beastServiceUnderTest.beastRepository.findByBeastName(Bestiary.ALP)).thenReturn(new Beast(Bestiary.ALP));
        when(beastServiceUnderTest.beastRepository.saveAndFlush(new Beast(Bestiary.ALP))).thenReturn(new Beast(Bestiary.ALP));

        // Run the test
        final Beast result = beastServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Beast model = new Beast(Bestiary.ALP);

        // Run the test
        beastServiceUnderTest.delete(model);

        // Verify the results
        verify(beastServiceUnderTest.beastRepository).delete(new Beast(Bestiary.ALP));
    }

    @Test
    public void testEdit() {
        // Setup
        final Beast model = new Beast(Bestiary.ALP);
        final Beast expectedResult = new Beast(Bestiary.ALP);
        when(beastServiceUnderTest.beastRepository.saveAndFlush(new Beast(Bestiary.ALP))).thenReturn(new Beast(Bestiary.ALP));

        // Run the test
        final Beast result = beastServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Beast expectedResult = new Beast(Bestiary.ALP);
        when(beastServiceUnderTest.beastRepository.findById(0L)).thenReturn(Optional.of(new Beast(Bestiary.ALP)));

        // Run the test
        final Beast result = beastServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_BeastRepositoryReturnsAbsent() {
        // Setup
        when(beastServiceUnderTest.beastRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Beast result = beastServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup
        when(beastServiceUnderTest.beastRepository.findAll()).thenReturn(Collections.singletonList(new Beast(Bestiary.ALP)));

        // Run the test
        final Iterable<Beast> result = beastServiceUnderTest.getAll();

        // Verify the results
    }
}
