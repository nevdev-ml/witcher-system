package com.nevdev.witcher.application;

import com.nevdev.witcher.contracts.IService;
import com.nevdev.witcher.core.User;

public interface IUserService extends IService<User> {
    User find(String value);
}
