package com.example.springsecurity.service;

import com.example.springsecurity.domain.post.Posts;
import com.example.springsecurity.domain.post.PostsRepository;
import com.example.springsecurity.web.dto.PostsListResponseDto;
import com.example.springsecurity.web.dto.PostsResponseDto;
import com.example.springsecurity.web.dto.PostsSaveRequestDto;
import com.example.springsecurity.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found Posts with id " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto dto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found Posts with id " + id));

        posts.update(dto.getTitle(), dto.getContent());

        return id;
    }

    @Transactional
    public void deleteById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not found Posts with id " + id));

        postsRepository.delete(posts);
    }
}
