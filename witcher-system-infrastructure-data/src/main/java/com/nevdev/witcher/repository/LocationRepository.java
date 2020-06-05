package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Location;
import com.nevdev.witcher.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByRegion(Region region);
}
