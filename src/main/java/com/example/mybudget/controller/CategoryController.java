package com.example.mybudget.controller;

import com.example.mybudget.domain.Category;
import com.example.mybudget.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok("카테고리 추가 완료");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Category category) {
        categoryService.updateCategory(category);
        return ResponseEntity.ok("카테고리 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("카테고리 삭제 완료");
    }
}
