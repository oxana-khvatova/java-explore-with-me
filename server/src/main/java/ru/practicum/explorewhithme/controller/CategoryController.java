package ru.practicum.explorewhithme.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewhithme.dto.CategoryDto;
import ru.practicum.explorewhithme.mapper.CategoryMapper;
import ru.practicum.explorewhithme.model.Category;
import ru.practicum.explorewhithme.service.CategoryService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/categories/{id}")
    public CategoryDto getCategory(@PathVariable long id) {
        log.info("Запрошена катеория id: " + id);
        Category category = categoryService.findById(id);
        return categoryMapper.toCategoryDto(category);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAll(@RequestParam(required = false, defaultValue = "0") int from,
                                    @RequestParam(required = false, defaultValue = "20") int size) {
        List<Category> allCategories = new ArrayList<>(categoryService.findAll(from, size).toList());
        log.info("Категорий в базе: {}", allCategories.size());
        return categoryMapper.toCategoryDtoList(allCategories);
    }

    @PostMapping("/admin/categories")
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        Category savedCategory = categoryService.save(category);
        log.info("Новая категория: " + savedCategory);
        return categoryMapper.toCategoryDto(savedCategory);
    }

    @PatchMapping("/admin/categories")
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        Category categoryForUpdate = categoryService.upDate(category, category.getId());
        log.info("Update category: " + categoryForUpdate);
        return categoryMapper.toCategoryDto(categoryForUpdate);
    }

    @DeleteMapping("/admin/categories/{id}")
    public void deleteById(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
