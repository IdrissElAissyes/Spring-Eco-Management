package org.springtest1.springtest.dao.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContains(String keyword);

    List<Product> findByCategory(Category category);

@Query("SELECT p.category.name, COUNT(p) FROM Product p GROUP BY p.category.name")
List<Object[]> countProductsByCategory();
    @Modifying
    @Query("DELETE FROM Product p WHERE p.category.id = :categoryId")
    void deleteByCategoryId(@Param("categoryId") Long categoryId);
    List<Product> findByPrice(Integer pr);
    //    List<Product> findByfirstNameContaining(String mt);
}