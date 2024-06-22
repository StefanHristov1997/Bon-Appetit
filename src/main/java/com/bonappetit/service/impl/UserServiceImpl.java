package com.bonappetit.service.impl;

import com.bonappetit.model.dto.binding.UserLoginDTO;
import com.bonappetit.model.dto.binding.UserRegisterDTO;
import com.bonappetit.model.entity.User;
import com.bonappetit.repository.UserRepository;
import com.bonappetit.service.UserService;
import com.bonappetit.util.CurrentUserSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserSession currentUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CurrentUserSession currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }


    @Override
    public void registerUser(UserRegisterDTO userRegisterDTO) {
        User userToSave = modelMapper.map(userRegisterDTO, User.class);

        this.userRepository.save(userToSave);
    }

    @Override
    public boolean loginUser(UserLoginDTO userLoginDTO) {

        Optional<User> user = userRepository
                .findByUsername(userLoginDTO.getUsername());

        if (user.isPresent() && passwordEncoder.matches(userLoginDTO.getPassword(), user.get().getPassword())) {
            currentUser.logUser(user.get());
            return true;
        }

        return false;
    }

    @Override
    public void logoutUser() {
        this.currentUser.logout();
    }
}
