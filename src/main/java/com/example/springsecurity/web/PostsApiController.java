package com.example.springsecurity.web;

import com.example.springsecurity.domain.posts.Posts;
import com.example.springsecurity.service.PostsService;
import com.example.springsecurity.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping("/posts")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }
}
