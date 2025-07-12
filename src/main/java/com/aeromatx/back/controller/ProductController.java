// src/main/java/com/aeromatx/back/controller/ProductController.java
package com.aeromatx.back.controller;

import com.aeromatx.back.dto.product.ProductDTO;
import com.aeromatx.back.dto.product.ProductResponseDTO;
import com.aeromatx.back.entity.Product;
import com.aeromatx.back.repository.ProductRepository;
import com.aeromatx.back.service.ProductService;
import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;


@RestController
@RequestMapping("/api/products/admin")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAllProductDTOs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> add(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> edit(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> del(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

   @Autowired
private Cloudinary cloudinary;

@PostMapping("/{id}/image")
public ResponseEntity<?> uploadProductImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) throws IOException {
    Product p = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
    String imageUrl = (String) uploadResult.get("secure_url");

    p.setImageUrl(imageUrl);
    productRepository.save(p);

    return ResponseEntity.ok("Product image uploaded successfully.");
}
}
