package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {
    List<Deal> findByDone(Boolean done);

    List<Deal> findByPaid(Boolean paid);

    List<Deal> findBySaleAndDone(Boolean operation, Boolean done);

    List<Deal> findByTraderAndDone(Boolean trader, Boolean done);

    List<Deal> findByCustomerId(Long id);

    List<Deal> findByExecutorIdAndDone(Long executorId, Boolean done);
}
