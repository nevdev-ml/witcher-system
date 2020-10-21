package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    List<Bank> findByKingRepository(Boolean kingRepository);
}
