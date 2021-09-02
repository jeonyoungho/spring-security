package com.example.springsecurity.web;

import com.example.springsecurity.domain.post.Posts;
import com.example.springsecurity.exception.ErrorResponse;
import com.example.springsecurity.service.PostsService;
import com.example.springsecurity.web.dto.PostsListResponseDto;
import com.example.springsecurity.web.dto.PostsResponseDto;
import com.example.springsecurity.web.dto.PostsSaveRequestDto;
import com.example.springsecurity.web.dto.PostsUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "posts", description = "게시물 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/posts")
    @Operation(summary = "게시물 등록", description = "제목(title)과 내용(content)을 이용하여 게시물을 신규 등록합니다.")
    public Long save(@RequestBody PostsSaveRequestDto dto) {
        return postsService.save(dto);
    }

    @GetMapping("/posts")
    public List<PostsListResponseDto> findAll() {
        return postsService.findAllDesc();
    }

//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostsResponseDto.class))),
//            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    @Operation(summary = "게시글 조회", description = "id를 이용하여 posts 레코드를 조회합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostsResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/posts/{id}")
    public PostsResponseDto findById(@Parameter(name = "id", description = "posts 의 id", in = ParameterIn.PATH)
                                         @PathVariable Long id) {
        return postsService.findById(id);
    }

    @PutMapping("/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto dto) {
        return postsService.update(id, dto);
    }

    @DeleteMapping("/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.deleteById(id);
        return id;
    }

}
