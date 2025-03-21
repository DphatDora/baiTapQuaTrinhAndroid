package com.example.ProjectAPI.controller;

import com.example.ProjectAPI.DTO.CategoryDTO;
import com.example.ProjectAPI.model.Category;
import com.example.ProjectAPI.model.CategoryType;
import com.example.ProjectAPI.service.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cate")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryService;

    @GetMapping()
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategories();
        if (categoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getType(),
                        category.getImgCategory()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryDTOList);
    }

    @PostMapping("/add-category")
    public ResponseEntity<?> addCategory(@Validated @RequestParam("type") String type, @RequestParam("imgCategory") String imgCategory) {
        try {
            CategoryType categoryType = CategoryType.valueOf(type);

            Optional<Category> optCategory = categoryService.getCategoryByType(categoryType);
            if (optCategory.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category đã tồn tại trong hệ thống");
            } else {
                Category category = new Category();
                category.setType(categoryType);
                category.setImgCategory(imgCategory);
                categoryService.save(category);
                return ResponseEntity.ok().body(category);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CategoryType không hợp lệ.");
        }
    }
}
