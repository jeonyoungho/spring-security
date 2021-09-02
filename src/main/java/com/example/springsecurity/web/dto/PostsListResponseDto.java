package com.example.springsecurity.web.dto;

import com.example.springsecurity.domain.post.Posts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "게시물 리스트 응답DTO")
@Getter
public class PostsListResponseDto {
    @Schema(description = "일자")
    private Long id;

    @Schema(description = "게시물 제목", defaultValue = "디폴트 제목", allowableValues = {"게시물1", "게시물2"})
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "수정일자")
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.modifiedDate = posts.getModifiedDate();
    }
}
