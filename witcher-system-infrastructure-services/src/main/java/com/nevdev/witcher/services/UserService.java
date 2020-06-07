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
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public User create(User model) {
        User user = find(model.getUsername());
        if(user == null){
            return userRepository.saveAndFlush(model);
        }
        return user;
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
        if (id == null){
            return null;
        }
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }
}
