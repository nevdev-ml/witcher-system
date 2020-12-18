package com.nevdev.witcher.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deal_seq")
    @SequenceGenerator(name = "deal_seq", sequenceName = "deal_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completionOn;

    private String title;

    private String description;

    @NotNull
    private Long customerId;

    private Long executorId;


    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @MapsId
    @NotNull
    private Reward reward;

    @NotNull
    private Boolean done;

    @NotNull
    private Boolean paid;

    @NotNull
    private Boolean sale;

    @NotNull
    private Boolean trader;


    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"dealsBookmarked", "tasksCompleted", "tasks"})
    @JoinTable(
            name = "DEAL_USER_BOOKMARKED",
            joinColumns = {@JoinColumn(name = "DEAL_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> executorsBookmarked = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"deals", "tasksCompleted", "tasks"})
    @JoinTable(
            name = "DEAL_USER",
            joinColumns = {@JoinColumn(name = "DEAL_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> executors = new ArrayList<>();

    public Deal(){}

    public Deal(String title, String description, @NotNull Boolean trader, @NotNull Boolean sale, @NotNull Reward reward,
                @NotNull Long customerId){
        createOn = new Date(System.currentTimeMillis());
        done = false;
        paid = false;
        this.title = title;
        this.description = description;
        this.trader = trader;
        this.sale = sale;
        this.reward = reward;
        this.customerId = customerId;
    }

    public Deal(Deal deal){
        id = deal.id;
        createOn = deal.createOn;
        done = deal.done;
        paid = deal.paid;
        title = deal.title;
        description = deal.description;
        trader = deal.trader;
        sale = deal.sale;
        reward = deal.reward;
        customerId = deal.customerId;
        executorId = deal.executorId;
        completionOn = deal.completionOn;
        executorsBookmarked = deal.executorsBookmarked;
        executors = deal.executors;
    }
}
