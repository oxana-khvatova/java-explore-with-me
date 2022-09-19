package ru.practicum.explorewhithme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewhithme.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    Page<Category> findAll(Pageable page);
}
