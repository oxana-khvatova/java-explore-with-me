package ru.practicum.explorewhithme.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
//    @Pattern(regexp = "^\\S+$", message = "не должен содержать пробелы")
    private String name;
}
