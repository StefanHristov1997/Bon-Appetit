package com.bonappetit.controller;

import com.bonappetit.model.dto.UserLoginDTO;
import com.bonappetit.model.dto.UserRegisterDTO;
import com.bonappetit.service.UserService;
import com.bonappetit.util.CurrentUserSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping ("/users")
public class UserController {

    private final UserService userService;
    private final CurrentUserSession currentUserSession;

    @Value("${binding-result-package}")
    private String bindingResultPackage;

    private final String registerAttribute = "userRegisterDTO";
    private final String loginAttribute = "userLoginDTO";

    @Autowired
    public UserController(UserService userService, CurrentUserSession currentUserSession) {
        this.userService = userService;
        this.currentUserSession = currentUserSession;
    }


    @GetMapping("/register")
    public String viewRegister(Model model) {

        if (currentUserSession.isLogged()) {
            return "redirect:/home";
        }

        if (!model.containsAttribute(registerAttribute)) {
            model.addAttribute(registerAttribute, new UserRegisterDTO());
        }

        return "register";
    }

    @PostMapping("/register")
    public String doRegister(
            @Valid UserRegisterDTO userRegisterDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if (currentUserSession.isLogged()) {
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(registerAttribute, userRegisterDTO);
            rAtt.addFlashAttribute(bindingResultPackage + "." + registerAttribute, bindingResult);
            return "redirect:/users/register";
        }

        userService.registerUser(userRegisterDTO);

        return "redirect:/home";
    }

    @GetMapping("/login")
    public String viewLogin(Model model) {

        if(currentUserSession.isLogged()){
            return "redirect:/home";
        }

        if(!model.containsAttribute(loginAttribute)) {
            model.addAttribute(loginAttribute, new UserLoginDTO());
        }

        return "login";
    }

    @PostMapping("/login")
    public String doLogin(
            @Valid UserLoginDTO userLoginDTO,
            BindingResult bindingResult,
            RedirectAttributes rAtt) {

        if(currentUserSession.isLogged()){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute(loginAttribute, userLoginDTO);
            rAtt.addFlashAttribute(bindingResultPackage + "." + loginAttribute, bindingResult);
            return "redirect:/users/login";
        }

        boolean success = userService.loginUser(userLoginDTO);

        if(!success) {
            rAtt.addFlashAttribute(loginAttribute, userLoginDTO);
            rAtt.addFlashAttribute("badCredentials", true);
            return "redirect:/users/login";
        }

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String doLogout() {

        userService.logoutUser();

        return "redirect:/";
    }

}
