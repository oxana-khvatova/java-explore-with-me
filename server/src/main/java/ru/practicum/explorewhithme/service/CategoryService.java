package ru.practicum.explorewhithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewhithme.exception.CategoryNotFoundException;
import ru.practicum.explorewhithme.model.Category;
import ru.practicum.explorewhithme.repository.CategoryRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category upDate(Category category, Long id) {
        Category categoryUpDate = findById(id);
        if (category.getName() != null) {
            categoryUpDate.setName(category.getName());
        }
        return categoryRepository.save(categoryUpDate);
    }

    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else throw new CategoryNotFoundException("Категория не найдена");
    }

    public Page<Category> findAll(int from, int size) {
        Sort sortById = Sort.by(Sort.Direction.ASC, "id");
        Pageable page = PageRequest.of(from, size, sortById);
        return categoryRepository.findAll(page);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
