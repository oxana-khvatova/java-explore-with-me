package model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;
}
