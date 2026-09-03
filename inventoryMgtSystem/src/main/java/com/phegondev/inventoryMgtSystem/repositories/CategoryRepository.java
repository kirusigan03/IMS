package com.phegondev.inventoryMgtSystem.repositories;

import com.phegondev.inventoryMgtSystem.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
