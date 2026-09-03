package com.phegondev.inventoryMgtSystem.services;

import com.phegondev.inventoryMgtSystem.dtos.LoginRequest;
import com.phegondev.inventoryMgtSystem.dtos.RegisterRequest;
import com.phegondev.inventoryMgtSystem.dtos.Response;
import com.phegondev.inventoryMgtSystem.dtos.UserDTO;
import com.phegondev.inventoryMgtSystem.models.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);

    Response loginUser(LoginRequest loginRequest);

    Response getAllUsers();

    User getCurrentLoggedInUser();

    Response getUserById(Long id);

    Response updateUser(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUserTransactions(Long id);
}
