package com.nevdev.witcher.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordModifyModel {
    private String password;
    private Long userId;
}
