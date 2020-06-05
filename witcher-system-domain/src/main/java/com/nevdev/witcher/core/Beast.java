package com.nevdev.witcher.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nevdev.witcher.enums.Bestiary;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Beast {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beast_seq")
    @SequenceGenerator(name = "beast_seq", sequenceName = "beast_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    @Column(unique = true)
    private Bestiary beastName;

    @ManyToMany(
            mappedBy = "beasts",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("beasts")
    private List<Task> tasks;

    public Beast() {}

    public Beast(@NotNull Bestiary beastName) {
        this.beastName = beastName;
    }
}
