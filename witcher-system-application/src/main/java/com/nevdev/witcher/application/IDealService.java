package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Deal;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.core.User;

import java.util.List;

public interface IDealService extends IService<Deal> {
    List<Deal> findDone(Boolean done);

    List<Deal> findPaid(Boolean paid);

    List<Deal> findSaleAndDone(Boolean operatio, Boolean done);

    List<Deal> findTraderAndDone(Boolean trader, Boolean done);

    List<Deal> getActiveDeals(Long userId);

    List<Deal> getSuccessHistoryDeals(Long userId);

    List<Deal> getBookmarkedHistoryDeals(Long userId);

    List<Deal> getCustomerDeals(Long userId);

    void acceptDeal(User customer, User executor, Reward reward);
}
