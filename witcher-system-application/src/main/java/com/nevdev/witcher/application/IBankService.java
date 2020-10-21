package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Bank;

import java.util.List;

public interface IBankService extends IService<Bank> {
    List<Bank> find(Boolean kingRepository);
}
