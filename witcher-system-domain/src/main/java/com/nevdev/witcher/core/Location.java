package com.nevdev.witcher.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nevdev.witcher.enums.Region;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    @Column(unique = true)
    private Region region;

    @OneToMany(
            mappedBy = "location",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnoreProperties("location")
    private List<Task> tasks;

    public Location() {}

    public Location(@NotNull Region region){
        this.region = region;
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
        task.setLocation(this);
    }
}
