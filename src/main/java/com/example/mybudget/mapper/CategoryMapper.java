package com.example.mybudget.mapper;

import com.example.mybudget.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAllCategories();

    void insertCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(Long id);
}
