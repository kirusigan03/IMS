package com.phegondev.inventoryMgtSystem.services;

import com.phegondev.inventoryMgtSystem.dtos.CategoryDTO;
import com.phegondev.inventoryMgtSystem.dtos.Response;

public interface CategoryService {

    Response createCategory(CategoryDTO categoryDTO);

    Response getAllCategories();

    Response getCategoryById(Long id);

    Response updateCategory(Long id, CategoryDTO categoryDTO);

    Response deleteCategory(Long id);


}
