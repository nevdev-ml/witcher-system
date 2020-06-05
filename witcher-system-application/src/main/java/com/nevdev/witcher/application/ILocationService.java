package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Location;
import com.nevdev.witcher.enums.Region;

public interface ILocationService extends IService<Location> {
    Location find(Region regionName);
}
