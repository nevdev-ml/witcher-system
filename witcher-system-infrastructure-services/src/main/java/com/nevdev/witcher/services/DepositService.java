package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IDepositService;
import com.nevdev.witcher.core.Deposit;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositService implements IDepositService {
    @Autowired
    DepositRepository depositRepository;

    @Override
    public List<Deposit> find(Currency currencyType) {
        return depositRepository.findByType(currencyType);
    }

    @Override
    public Deposit create(Deposit model) {
        return depositRepository.saveAndFlush(model);
    }

    @Override
    public void delete(Deposit model) {
        depositRepository.delete(model);
    }

    @Override
    public Deposit edit(Deposit model) {
        return depositRepository.saveAndFlush(model);
    }

    @Override
    public Deposit get(Long id) {
        if (id == null){
            return null;
        }
        return depositRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Deposit> getAll() {
        return depositRepository.findAll();
    }
}
