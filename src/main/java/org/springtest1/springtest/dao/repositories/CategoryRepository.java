package org.springtest1.springtest.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springtest1.springtest.dao.entities.Category;
import org.springtest1.springtest.dao.entities.Product;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
