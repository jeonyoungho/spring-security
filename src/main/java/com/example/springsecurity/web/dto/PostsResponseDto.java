package com.example.springsecurity.web.dto;

import com.example.springsecurity.domain.post.Posts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
    }

}
