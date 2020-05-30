package com.nevdev.witcher.services;

import com.nevdev.witcher.application.IUserService;
import com.nevdev.witcher.core.User;
import com.nevdev.witcher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User find(String value) {
        return userRepository.findByUsername(value);
    }

    @Override
    public User create(User model) {
        return userRepository.saveAndFlush(model);
    }

    @Override
    public void delete(User model) {
        userRepository.delete(model);
    }

    @Override
    public User edit(User model) {
        return userRepository.saveAndFlush(model);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }
}
