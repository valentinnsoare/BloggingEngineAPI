package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.CategoryDto;
import io.valentinsoare.newsoutletapis.entity.Category;
import io.valentinsoare.newsoutletapis.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    boolean existsByName(String name);
    List<CategoryDto> getCategoriesByIds(List<Long> categoryIds);
    CategoryDto getCategoryById(Long id);
    CategoryDto getCategoryByName(String name);
    CategoryDto addCategory(CategoryDto category);
    void deleteCategory(Long id);
    void updateCategory(Long id, CategoryDto category);
    CategoryResponse getAllCategories(int pageNo, int pageSize, String sortBy, String sortDir);
    long countAllCategories();
    void deleteAllCategories();
}
