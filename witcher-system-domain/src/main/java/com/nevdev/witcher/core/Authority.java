package com.nevdev.witcher.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nevdev.witcher.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NAME", length = 50, unique = true)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role roleName;

    @ManyToMany(
            mappedBy = "authorities",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("authorities")
    private List<User> users;

    public Authority(){}
}
