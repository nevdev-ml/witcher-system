package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Beast;
import com.nevdev.witcher.enums.Bestiary;

public interface IBeastService extends IService<Beast> {
    Beast find(Bestiary beastName);
}
