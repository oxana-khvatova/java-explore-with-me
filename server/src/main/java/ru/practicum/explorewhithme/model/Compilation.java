package ru.practicum.explorewhithme.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "compilations_event",
            joinColumns = { @JoinColumn(name = "compilation_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    private Set<Event> events;
}
