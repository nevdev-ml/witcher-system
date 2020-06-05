package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.enums.Currency;

import java.util.List;

public interface IRewardService extends IService<Reward> {
    List<Reward> find(Currency currencyType);
}
