package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IRewardService;
import com.nevdev.witcher.core.Reward;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardService implements IRewardService {
    @Autowired
    RewardRepository rewardRepository;

    @Override
    public List<Reward> find(Currency currencyType) {
        return rewardRepository.findByType(currencyType);
    }

    @Override
    public Reward create(Reward model) {
        return rewardRepository.saveAndFlush(model);
    }

    @Override
    public void delete(Reward model) {
        rewardRepository.delete(model);
    }

    @Override
    public Reward edit(Reward model) {
        return rewardRepository.saveAndFlush(model);
    }

    @Override
    public Reward get(Long id) {
        return rewardRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Reward> getAll() {
        return rewardRepository.findAll();
    }
}
