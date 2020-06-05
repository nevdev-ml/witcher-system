package com.nevdev.witcher.repository;

import com.nevdev.witcher.core.Beast;
import com.nevdev.witcher.enums.Bestiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeastRepository extends JpaRepository<Beast, Long> {
    Beast findByBeastName(Bestiary beastName);
}
