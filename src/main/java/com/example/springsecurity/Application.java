package com.example.springsecurity;

import com.example.springsecurity.domain.posts.Posts;
import com.example.springsecurity.domain.posts.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final PostsRepository postsRepository;

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
    }
}
