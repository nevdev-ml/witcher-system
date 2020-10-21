package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Deposit;
import com.nevdev.witcher.enums.Currency;

import java.util.List;

public interface IDepositService extends IService<Deposit> {
    List<Deposit> find(Currency currencyType);
}
