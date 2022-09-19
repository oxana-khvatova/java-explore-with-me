package ru.practicum.explorewhithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewhithme.dto.CategoryDto;
import ru.practicum.explorewhithme.model.Category;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        List<CategoryDto> listDto = new ArrayList<CategoryDto>();
        if (categories.size() == 0) {
            return listDto;
        }
        for (Category category : categories) {
            CategoryDto dto = toCategoryDto(category);
            listDto.add(dto);
        }
        return listDto;
    }
}
