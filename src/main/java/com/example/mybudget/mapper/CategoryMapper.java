package com.example.mybudget.mapper;

import com.example.mybudget.dto.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAllCategories();
}
