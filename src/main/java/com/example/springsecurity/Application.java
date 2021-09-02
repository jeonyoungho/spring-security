package com.example.springsecurity;

import com.example.springsecurity.domain.post.Posts;
import com.example.springsecurity.domain.post.PostsRepository;
import com.example.springsecurity.domain.user.User;
import com.example.springsecurity.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<10;i++) {
            postsRepository.save(Posts.builder()
                .title("title" + i)
                .content("content" + i)
                .build());
        }

        userRepository.save(User.builder()
                    .username("alice")
                    .password(passwordEncoder.encode("alicepw"))
                    .build());
    }
}
