package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.mapper.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        log.info("Category created with id {}", category.getId());
        return CategoryMapper.toCategoryDto(category);
    }

    public CategoryDto update(long catId, CategoryDto categoryDto) {
//        TODO дописать исключение
        Category category = categoryRepository.findById(catId).orElseThrow();
        category.setName(categoryDto.getName());
        log.info("Category with id {} updated", catId);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    public void delete(long catId) {
//        TODO дописать исключение
        categoryRepository.findById(catId).orElseThrow();
        categoryRepository.deleteById(catId);
        log.info("Category with id {} deleted", catId);
    }

    public List<CategoryDto> findAll(Pageable pageable) {
        log.info("Categories sent");
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto findById(long catId) {
//        TODO дописать эксепшн
        Category category = categoryRepository.findById(catId).orElseThrow();
        log.info("Category with id {} sent", catId);
        return CategoryMapper.toCategoryDto(category);
    }
}
