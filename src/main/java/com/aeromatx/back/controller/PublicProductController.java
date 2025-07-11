package com.aeromatx.back.controller;

import com.aeromatx.back.dto.product.ProductResponseDTO;
import com.aeromatx.back.entity.Product;
import com.aeromatx.back.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:5500"})
public class PublicProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        return ResponseEntity.ok(productService.getAllProductDTOs());
    }
}
