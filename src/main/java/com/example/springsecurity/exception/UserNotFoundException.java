package com.example.springsecurity.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username){
        super(username + " NotFoundException");
    }

}
