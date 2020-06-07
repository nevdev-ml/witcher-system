package com.nevdev.witcher.models;

import com.nevdev.witcher.core.Task;
import com.nevdev.witcher.core.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskViewModel extends Task {
    private User customer;
    private User witcher;

    public TaskViewModel(Task task, User customer, User witcher) {
        super(task);
        this.customer = customer;
        this.witcher = witcher;
    }
}
