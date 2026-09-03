package com.phegondev.inventoryMgtSystem.services;

import com.phegondev.inventoryMgtSystem.dtos.Response;
import com.phegondev.inventoryMgtSystem.dtos.TransactionRequest;
import com.phegondev.inventoryMgtSystem.enums.TransactionStatus;

public interface TransactionService {
    Response purchase(TransactionRequest transactionRequest);

    Response sell(TransactionRequest transactionRequest);

    Response returnToSupplier(TransactionRequest transactionRequest);

    Response getAllTransactions(int page, int size, String filter);

    Response getAllTransactionById(Long id);

    Response getAllTransactionByMonthAndYear(int month, int year);

    Response updateTransactionStatus(Long transactionId, TransactionStatus status);
}
