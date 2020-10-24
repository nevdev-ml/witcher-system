package com.nevdev.witcher.core;

import com.nevdev.witcher.enums.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reward {
    private static final double taxConstant = .1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reward_seq")
    @SequenceGenerator(name = "reward_seq", sequenceName = "reward_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private Double reward;

    @NotNull
    private Double tax;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private Currency type;

    @AssertTrue
    public boolean getValueCheck() {
        return reward > 0;
    }

    @AssertTrue
    public boolean getTaxCheck() {
        return tax > 0;
    }

    public Reward() {}

    public Reward(@NotNull Double reward, @NotNull Currency type){
        this.reward = reward - (reward * taxConstant);
        setTax(reward);
        this.type = type;
    }

    private void setTax(Double reward){
        this.tax = reward * taxConstant;
    }

    public void setReward(Double reward){
        this.reward = reward - (reward * taxConstant);
        setTax(reward);
    }
}
