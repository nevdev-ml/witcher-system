package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IBeastService;
import com.nevdev.witcher.core.Beast;
import com.nevdev.witcher.enums.Bestiary;
import com.nevdev.witcher.repository.BeastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeastService implements IBeastService {
    @Autowired
    BeastRepository beastRepository;

    @Override
    public Beast find(Bestiary beastName) {
        return beastRepository.findByBeastName(beastName);
    }

    @Override
    public Beast create(Beast model) {
        Beast beast = find(model.getBeastName());
        if(beast == null){
            return beastRepository.saveAndFlush(model);
        }
        return beast;
    }

    @Override
    public void delete(Beast model) {
        beastRepository.delete(model);
    }

    @Override
    public Beast edit(Beast model) {
        return beastRepository.saveAndFlush(model);
    }

    @Override
    public Beast get(Long id) {
        return beastRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Beast> getAll() {
        return beastRepository.findAll();
    }
}
