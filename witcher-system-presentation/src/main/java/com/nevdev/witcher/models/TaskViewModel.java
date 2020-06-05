package com.nevdev.witcher.models;

import com.nevdev.witcher.core.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskViewModel extends Task {
    private String checkedLocation;
    private String checkedCurrency;
    private Double rewardValue;
    private String checkedBeast;

}
