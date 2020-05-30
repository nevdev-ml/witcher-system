package com.nevdev.witcher.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reward {
    @EqualsAndHashCode.Include
    private long id;

    private int value;

    @Enumerated(EnumType.ORDINAL)
    private Currency type;

}

