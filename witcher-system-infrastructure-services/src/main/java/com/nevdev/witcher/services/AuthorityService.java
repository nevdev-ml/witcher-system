package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IAuthorityService;
import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.core.Role;
import com.nevdev.witcher.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority find(Role authorityName) {
        return authorityRepository.findByName(authorityName);
    }

    @Override
    public Authority create(Authority model) {
        return authorityRepository.saveAndFlush(model);
    }

    @Override
    public void delete(Authority model) {
        authorityRepository.delete(model);
    }

    @Override
    public Authority edit(Authority model) {
        return authorityRepository.saveAndFlush(model);
    }

    @Override
    public Authority get(Long id) {
        return authorityRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Authority> getAll() {
        return authorityRepository.findAll();
    }
}
