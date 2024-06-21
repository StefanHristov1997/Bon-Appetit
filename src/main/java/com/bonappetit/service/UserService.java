package com.bonappetit.service;

import com.bonappetit.model.dto.UserLoginDTO;
import com.bonappetit.model.dto.UserRegisterDTO;

public interface UserService {


    void registerUser(UserRegisterDTO userRegisterDTO);

    boolean loginUser(UserLoginDTO userLoginDTO);
}
