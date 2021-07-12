package com.example.springsecurity.web;

import com.example.springsecurity.domain.user.User;
import com.example.springsecurity.domain.user.UserRepository;
import com.example.springsecurity.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() { return "joinForm"; }

    @PostMapping("/join")
    public String join(User user) {
        user.updateEncodedPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/user/loginForm";
    }
}
