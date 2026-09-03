package com.phegondev.inventoryMgtSystem.repositories;

import com.phegondev.inventoryMgtSystem.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
