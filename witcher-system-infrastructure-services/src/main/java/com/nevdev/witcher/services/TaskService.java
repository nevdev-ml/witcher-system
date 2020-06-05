package com.nevdev.witcher.services;

import com.nevdev.witcher.application.ITaskService;
import com.nevdev.witcher.core.Beast;
import com.nevdev.witcher.core.Task;
import com.nevdev.witcher.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskService implements ITaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    BeastService beastService;

    @Autowired
    LocationService locationService;

    @Autowired
    UserService userService;

    @Override
    public List<Task> find(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> find(Boolean done) {
        return taskRepository.findByDone(done);
    }

    @Override
    public List<Task> getAllQuests(Long userId) {
        return StreamSupport.stream(getAll().spliterator(), false)
                .collect(Collectors.toList()).stream().filter(item ->
                        item.getWitchers().stream().anyMatch(u ->
                                u.getId().equals(userId))).collect(Collectors.toList());
    }

    @Override
    public Task create(Task model) {
        List<Beast> beasts = new ArrayList<>();
        for(Beast item : model.getBeasts()){
            beasts.add(beastService.create(item));
        }
        model.setBeasts(beasts);
        model.setLocation(locationService.create(model.getLocation()));
        return taskRepository.save(model);
    }

    @Override
    public void delete(Task model) {
        taskRepository.delete(model);
    }

    @Override
    public Task edit(Task model) {
        return taskRepository.save(model);
    }

    @Override
    public Task get(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }
}
