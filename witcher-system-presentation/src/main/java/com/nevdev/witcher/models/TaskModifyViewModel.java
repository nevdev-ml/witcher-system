package com.nevdev.witcher.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskModifyViewModel {
    private String checkedLocation;
    private String checkedCurrency;
    private Double checkedRewardValue;
    private String checkedBeast;

    private String title;
    private String locationComment;
}
