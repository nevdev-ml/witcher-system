package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IAuthorityService;
import com.nevdev.witcher.core.Authority;
import com.nevdev.witcher.enums.Role;
import com.nevdev.witcher.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService {
    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public Authority find(Role authorityName) {
        return authorityRepository.findByRoleName(authorityName);
    }

    @Override
    public Authority create(Authority model) {
        Authority authority = find(model.getRoleName());
        if(authority == null){
            return authorityRepository.saveAndFlush(model);
        }
        return authority;
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
        if (id == null){
            return null;
        }
        return authorityRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Authority> getAll() {
        return authorityRepository.findAll();
    }
}
