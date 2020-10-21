package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.Task;

import java.util.List;

public interface ITaskService extends IService<Task> {
    List<Task> find(String title);

    List<Task> find(Boolean done);

    List<Task> getActiveQuests(Long userId);

    List<Task> getWinHistoryQuests(Long userId);

    List<Task> getLoseHistoryQuests(Long userId);
}
