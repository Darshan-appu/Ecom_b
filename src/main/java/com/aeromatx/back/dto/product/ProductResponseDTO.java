// src/main/java/com/aeromatx/back/dto/product/ProductResponseDTO.java
package com.aeromatx.back.dto.product;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private BigDecimal price;
    private int stock;
    private String imageUrl;

    private String categoryName;
    private String subCategoryName;

    private List<SpecificationDTO> specifications;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SpecificationDTO {
        private String key;
        private String value;
    }
}
