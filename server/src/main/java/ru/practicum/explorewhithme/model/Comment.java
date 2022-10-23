package ru.practicum.explorewhithme.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NotNull
    private String text;
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "author_id")
    private Long authorId;
    private LocalDateTime created;
}
