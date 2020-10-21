package com.nevdev.witcher.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nevdev.witcher.enums.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deposit_seq")
    @SequenceGenerator(name = "deposit_seq", sequenceName = "deposit_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private Currency type;

    @NotNull
    private Double balance;

    @ManyToMany(
            mappedBy = "deposits",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("deposits")
    private List<Bank> banks = new ArrayList<>();

    public Deposit(){}

    public Deposit(@NotNull Currency type, @NotNull Double balance){
        this.type = type;
        this.balance = balance;
    }
}
