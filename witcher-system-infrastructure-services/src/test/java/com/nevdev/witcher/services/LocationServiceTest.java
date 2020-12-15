package com.nevdev.witcher.services;

import com.nevdev.witcher.core.Location;
import com.nevdev.witcher.enums.Region;
import com.nevdev.witcher.repository.LocationRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class LocationServiceTest {

    private LocationService locationServiceUnderTest;

    @Before
    public void setUp() {
        locationServiceUnderTest = new LocationService();
        locationServiceUnderTest.locationRepository = mock(LocationRepository.class);
    }

    @Test
    public void testFind() {
        // Setup
        final Location expectedResult = new Location(Region.AEDIRN);
        when(locationServiceUnderTest.locationRepository.findByRegion(Region.AEDIRN)).thenReturn(new Location(Region.AEDIRN));

        // Run the test
        final Location result = locationServiceUnderTest.find(Region.AEDIRN);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testCreate() {
        // Setup
        final Location model = new Location(Region.AEDIRN);
        final Location expectedResult = new Location(Region.AEDIRN);
        when(locationServiceUnderTest.locationRepository.findByRegion(Region.AEDIRN)).thenReturn(new Location(Region.AEDIRN));
        when(locationServiceUnderTest.locationRepository.saveAndFlush(new Location(Region.AEDIRN))).thenReturn(new Location(Region.AEDIRN));

        // Run the test
        final Location result = locationServiceUnderTest.create(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testDelete() {
        // Setup
        final Location model = new Location(Region.AEDIRN);

        // Run the test
        locationServiceUnderTest.delete(model);

        // Verify the results
        verify(locationServiceUnderTest.locationRepository).delete(new Location(Region.AEDIRN));
    }

    @Test
    public void testEdit() {
        // Setup
        final Location model = new Location(Region.AEDIRN);
        final Location expectedResult = new Location(Region.AEDIRN);
        when(locationServiceUnderTest.locationRepository.findByRegion(Region.AEDIRN)).thenReturn(new Location(Region.AEDIRN));
        when(locationServiceUnderTest.locationRepository.save(new Location(Region.AEDIRN))).thenReturn(new Location(Region.AEDIRN));

        // Run the test
        final Location result = locationServiceUnderTest.edit(model);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet() {
        // Setup
        final Location expectedResult = new Location(Region.AEDIRN);
        when(locationServiceUnderTest.locationRepository.findById(0L)).thenReturn(Optional.of(new Location(Region.AEDIRN)));

        // Run the test
        final Location result = locationServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testGet_LocationRepositoryReturnsAbsent() {
        // Setup
        when(locationServiceUnderTest.locationRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Location result = locationServiceUnderTest.get(0L);

        // Verify the results
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void testGetAll() {
        // Setup
        when(locationServiceUnderTest.locationRepository.findAll()).thenReturn(Collections.singletonList(new Location(Region.AEDIRN)));

        // Run the test
        final Iterable<Location> result = locationServiceUnderTest.getAll();

        // Verify the results
    }
}
