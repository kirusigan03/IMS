package com.phegondev.inventoryMgtSystem.services;

import com.phegondev.inventoryMgtSystem.dtos.Response;
import com.phegondev.inventoryMgtSystem.dtos.SupplierDTO;

public interface SupplierService {

    Response addSupplier(SupplierDTO supplierDTO);

    Response updateSupplier(Long id, SupplierDTO supplierDTO);

    Response getAllSupplier();

    Response getSupplierById(Long id);

    Response deleteSupplier(Long id);
}
