package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IDealService;
import com.nevdev.witcher.core.*;
import com.nevdev.witcher.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DealService implements IDealService {
    @Autowired
    DealRepository dealRepository;

    @Autowired
    TaskService taskService;

    @Override
    public List<Deal> findDone(Boolean done) {
        return dealRepository.findByDone(done);
    }

    @Override
    public List<Deal> findPaid(Boolean paid) {
        return dealRepository.findByPaid(paid);
    }

    @Override
    public List<Deal> findSaleAndDone(Boolean operation, Boolean done) {
        return dealRepository.findBySaleAndDone(operation, done);
    }

    @Override
    public List<Deal> findTraderAndDone(Boolean trader, Boolean done) {
        return dealRepository.findByTraderAndDone(trader, done);
    }

    @Override
    public List<Deal> getActiveDeals(Long userId) {
        return Stream.of(
                new ArrayList<>(findDone(false)).stream()
                        .filter(item -> item.getExecutors().stream()
                                .anyMatch(u -> u.getId().equals(userId)))
                        .collect(Collectors.toList()),
                new ArrayList<>(findDone(false)).stream()
                        .filter(item -> item.getExecutorsBookmarked().stream()
                                .anyMatch(u -> u.getId().equals(userId)))
                        .collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<Deal> getSuccessHistoryDeals(Long userId) {
        return dealRepository.findByExecutorIdAndDone(userId, true);
    }

    @Override
    public List<Deal> getBookmarkedHistoryDeals(Long userId) {
        return new ArrayList<>(findDone(true)).stream()
                .filter(item -> item.getExecutorsBookmarked().stream()
                        .anyMatch(u -> u.getId().equals(userId)) && !item.getExecutorId().equals(userId) && item.getDone())
                .collect(Collectors.toList());
    }

    @Override
    public List<Deal> getCustomerDeals(Long userId) {
        return dealRepository.findByCustomerId(userId);
    }

    @Override
    public void acceptDeal(User customer, User executor, Reward reward) {
        taskService.rewardQuest(customer, executor, reward);
    }

    @Override
    public Deal create(Deal model) {
        Deal deal = get(model.getId());
        if(deal == null){
            return dealRepository.saveAndFlush(model);
        }
        return deal;
    }

    @Override
    public void delete(Deal model) {
        model.setExecutors(null);
        model.setExecutorsBookmarked(null);
        dealRepository.delete(model);
        dealRepository.flush();
    }

    @Override
    public Deal edit(Deal model) {
        return dealRepository.save(model);
    }

    @Override
    public Deal get(Long id) {
        if (id == null){
            return null;
        }
        return dealRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Deal> getAll() {
        return dealRepository.findAll();
    }
}
