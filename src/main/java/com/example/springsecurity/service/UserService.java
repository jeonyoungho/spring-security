package com.example.springsecurity.service;

import com.example.springsecurity.domain.user.User;
import com.example.springsecurity.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public User login(User user) {
//        return userRepository.findByUserIdAndUserPw(user.getUsername(), user.getPassword());
//    }
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public User findUserByUserId(String userId) {
//        return userRepository.findByUserId(userId).get();
//    }
}
