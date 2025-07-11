package com.aeromatx.back.controller;

import com.aeromatx.back.dto.product.CategoryDTO;
import com.aeromatx.back.entity.Category;
import com.aeromatx.back.repository.CategoryRepository;
import com.aeromatx.back.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping(value = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategoriesDTO() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> dtos = categories.stream()
                .map(cat -> categoryService.convertToDTO(cat, true))
                .toList();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getActiveCategoriesForFrontend() {
        List<Category> activeCategories = categoryService.getActiveCategories();
        return new ResponseEntity<>(activeCategories, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(category -> new ResponseEntity<>(categoryService.convertToDTO(category, true), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/admin/{id}", consumes = {"application/json", "application/json;charset=UTF-8"})
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // ✅ Upload category image with cleaned filename
    @PostMapping("/admin/{id}/image")
    public ResponseEntity<?> uploadCategoryImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        String cleanedFileName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        String fileName = UUID.randomUUID() + "_" + cleanedFileName;

        Path imagePath = Paths.get("uploads/categories/" + fileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, file.getBytes());

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setImageUrl("/uploads/categories/" + fileName);
        categoryRepository.save(category);

        return ResponseEntity.ok("Image uploaded successfully.");
    }
}
