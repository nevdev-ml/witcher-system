package com.nevdev.witcher.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DealModifyViewModel {
    private String checkedType;
    private String checkedCurrency;
    private Double checkedRewardValue;

    private String title;
    private String description;
}
