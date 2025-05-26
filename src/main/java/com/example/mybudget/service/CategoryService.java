package com.example.mybudget.service;

import com.example.mybudget.domain.Category;
import com.example.mybudget.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryMapper.findAllCategories();
    }

    public void addCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    public void deleteCategory(Long id) {
        categoryMapper.deleteCategory(id);
    }
}
