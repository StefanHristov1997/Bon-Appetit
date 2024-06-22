package com.bonappetit.service;

import com.bonappetit.model.dto.binding.UserLoginDTO;
import com.bonappetit.model.dto.binding.UserRegisterDTO;

public interface UserService {


    void registerUser(UserRegisterDTO userRegisterDTO);

    boolean loginUser(UserLoginDTO userLoginDTO);

    void logoutUser();
}
