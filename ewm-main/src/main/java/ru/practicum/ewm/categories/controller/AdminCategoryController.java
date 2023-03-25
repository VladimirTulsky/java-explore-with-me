package ru.practicum.ewm.categories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public Object create(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @PatchMapping("/{catId}")
    public Object update(@Positive @PathVariable Long catId,
                         @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.update(catId, categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@Positive @PathVariable Long catId) {
        categoryService.delete(catId);
    }
}