package com.nevdev.witcher.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nevdev.witcher.enums.Currency;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_seq")
    @SequenceGenerator(name = "bank_seq", sequenceName = "bank_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    private Boolean kingRepository;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("banks")
    @JoinTable(
            name = "BANK_DEPOSIT",
            joinColumns = {@JoinColumn(name = "BANK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DEPOSIT_ID")})
    private List<Deposit> deposits;

    public Bank(){
        Deposit orenDeposit = new Deposit(Currency.OREN, 0d);
        Deposit ducatDeposit = new Deposit(Currency.DUCAT, 0d);
        Deposit crownDeposit = new Deposit(Currency.CROWN, 0d);
        this.deposits = new ArrayList<>(Arrays.asList(orenDeposit, ducatDeposit, crownDeposit));
        this.kingRepository = false;
    }

    public Bank(List<Deposit> deposits){
        this.kingRepository = false;
        this.deposits = deposits;
    }

    public Bank(Boolean kingRepository){
        Deposit orenDeposit = new Deposit(Currency.OREN, 0d);
        Deposit ducatDeposit = new Deposit(Currency.DUCAT, 0d);
        Deposit crownDeposit = new Deposit(Currency.CROWN, 0d);
        this.deposits = new ArrayList<>(Arrays.asList(orenDeposit, ducatDeposit, crownDeposit));
        this.kingRepository = kingRepository;
    }
}
