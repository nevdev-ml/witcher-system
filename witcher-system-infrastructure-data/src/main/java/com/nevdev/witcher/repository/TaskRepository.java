package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitle(String title);

    List<Task> findByDone(Boolean done);

    List<Task> findByWitcherIdAndDone(Long witcherId, Boolean done);
}
