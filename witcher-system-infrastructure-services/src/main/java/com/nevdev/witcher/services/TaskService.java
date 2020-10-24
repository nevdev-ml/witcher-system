package com.nevdev.witcher.services;

import com.nevdev.witcher.application.ITaskService;
import com.nevdev.witcher.core.*;
import com.nevdev.witcher.enums.Currency;
import com.nevdev.witcher.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaskService implements ITaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    BeastService beastService;

    @Autowired
    LocationService locationService;

    @Autowired
    BankService bankService;

    @Autowired
    DepositService depositService;

    @Override
    public List<Task> find(String title) {
        return taskRepository.findByTitle(title);
    }

    @Override
    public List<Task> find(Boolean done) {
        return taskRepository.findByDone(done);
    }

    @Override
    public List<Task> getActiveQuests(Long userId) {
        return new ArrayList<>(find(false)).stream()
                .filter(item -> item.getWitchers().stream()
                        .anyMatch(u -> u.getId().equals(userId)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getWinHistoryQuests(Long userId) {
        return taskRepository.findByWitcherIdAndDone(userId, true);
    }

    @Override
    public List<Task> getLoseHistoryQuests(Long userId) {
        return new ArrayList<>(find(true)).stream()
                .filter(item -> item.getWitchers().stream()
                        .anyMatch(u -> u.getId().equals(userId)) && !item.getWitcherId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getCustomerQuests(Long userId) {
        return taskRepository.findByCustomerId(userId);
    }

    @Override
    public void rewardQuest(User customer, User witcher, Reward reward) {
        Currency currency = reward.getType();
        Deposit customerDeposit = bankService.getDeposits(customer.getBank(), currency).get(0);
        Deposit witcherDeposit = bankService.getDeposits(witcher.getBank(), currency).get(0);
        List<Bank> kingBanks = bankService.find(true);
        Double tax = reward.getTax() / kingBanks.size();
        for(Bank bank : kingBanks){
            Deposit kingDeposit = bankService.getDeposits(bank, currency).get(0);
            kingDeposit.setBalance(kingDeposit.getBalance() + tax);
            depositService.edit(kingDeposit);
        }
        customerDeposit.setBalance(customerDeposit.getBalance() - (reward.getTax() + reward.getReward()));
        witcherDeposit.setBalance(witcherDeposit.getBalance() + reward.getReward());
        depositService.edit(customerDeposit);
        depositService.edit(witcherDeposit);
    }

    @Override
    public Task create(Task model) {
        return modify(model);
    }

    @Override
    public void delete(Task model) {
        model.setWitchersCompleted(null);
        model.setWitchers(null);
        model.setBeasts(null);
        model.getLocation().getTasks().remove(model);
        taskRepository.delete(model);
        taskRepository.flush();
    }

    @Override
    public Task edit(Task model) {
        return modify(model);
    }

    @Override
    public Task get(Long id) {
        if (id == null){
            return null;
        }
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Task> getAll() {
        return taskRepository.findAll();
    }

    private Task modify(Task model){
        List<Beast> beasts = new ArrayList<>();
        for(Beast item : model.getBeasts()){
            beasts.add(beastService.create(item));
        }
        model.setBeasts(beasts);
        Location location = locationService.create(model.getLocation());
        location.addTask(model);
        return taskRepository.save(model);
    }
}
