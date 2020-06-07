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
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;

    private String locationComment;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("tasks")
    @NotNull
    private Location location;

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

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date createOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date completionOn;

    @NotNull
    private Long customerId;

    private Long witcherId;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("tasks")
    @JoinTable(
            name = "TASK_WITCHER",
            joinColumns = {@JoinColumn(name = "TASK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> witchers = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL
    )
    @JsonIgnoreProperties("tasks")
    @JoinTable(
            name = "TASK_BEAST",
            joinColumns = {@JoinColumn(name = "TASK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "BEAST_ID")})
    private List<Beast> beasts = new ArrayList<>();

    public Task() {}

    public Task(String title, String locationComment, @NotNull Location location, @NotNull Reward reward,
                @NotNull Long customerId, List<Beast> beasts){
        createOn = new Date(System.currentTimeMillis());
        done = false;
        paid = false;
        this.title = title;
        this.locationComment = locationComment;
        this.location = location;
        this.reward = reward;
        this.customerId = customerId;
        this.beasts = beasts;
    }

    public Task(Task task){
        id = task.id;
        createOn = task.createOn;
        done = task.done;
        paid = task.paid;
        title = task.title;
        locationComment = task.locationComment;
        location = task.location;
        reward = task.reward;
        customerId = task.customerId;
        beasts = task.beasts;
        completionOn = task.completionOn;
        witcherId = task.witcherId;
        witchers = task.witchers;
    }
}
