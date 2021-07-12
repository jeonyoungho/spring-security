package com.example.springsecurity.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // username을 가지고 User 정보를 가져올 수 있게 메소드 생성
    Optional<User> findByUsername(String username);

}
