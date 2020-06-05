package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findByType(Currency type);
}
