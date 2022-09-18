package model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "compilations_event")
public class CompilationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "compilation_id")
    private Long compilationId;
    @Column(name = "event_id")
    private Long eventId;
}
