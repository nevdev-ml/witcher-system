package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Deposit;
import com.nevdev.witcher.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    List<Deposit> findByType(Currency type);
}
