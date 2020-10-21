package com.nevdev.witcher.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskListViewModel {
    private List<TaskViewModel> active;
    private List<TaskViewModel> win;
    private List<TaskViewModel> lose;

    public TaskListViewModel(List<TaskViewModel> active, List<TaskViewModel> win,
                             List<TaskViewModel> lose){
        this.active = active;
        this.win = win;
        this.lose = lose;
    }
}
