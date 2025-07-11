// src/main/java/com/aeromatx/back/repository/ProductRepository.java
package com.aeromatx.back.repository;

import com.aeromatx.back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p FROM Product p " +
       "LEFT JOIN FETCH p.subCategory sc " +
       "LEFT JOIN FETCH sc.category c " +
       "LEFT JOIN FETCH p.specifications")
List<Product> findAllWithRelations();

}
