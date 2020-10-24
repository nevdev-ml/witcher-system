package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IBankService;
import com.nevdev.witcher.core.Bank;
import com.nevdev.witcher.core.Deposit;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService implements IBankService {
    @Autowired
    private BankRepository bankRepository;

    @Override
    public List<Bank> find(Boolean kingRepository) {
        return bankRepository.findByKingRepository(kingRepository);
    }

    @Override
    public List<Deposit> getDeposits(Bank bank, Currency currency) {
        if (bank == null || currency == null){
            return null;
        }
        return bank.getDeposits().stream().filter(deposit -> deposit.getType() == currency).collect(Collectors.toList());
    }

    @Override
    public Bank create(Bank model) {
        return bankRepository.saveAndFlush(model);
    }

    @Override
    public void delete(Bank model) {
        bankRepository.delete(model);
    }

    @Override
    public Bank edit(Bank model) {
        return bankRepository.saveAndFlush(model);
    }

    @Override
    public Bank get(Long id) {
        return bankRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Bank> getAll() {
        return bankRepository.findAll();
    }
}
