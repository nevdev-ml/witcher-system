package com.nevdev.witcher.models;

import com.nevdev.witcher.core.Deal;
import com.nevdev.witcher.core.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealViewModel extends Deal {
    private User customer;
    private User executor;

    public DealViewModel(Deal deal, User customer, User executor) {
        super(deal);
        this.customer = customer;
        this.executor = executor;
    }
}
