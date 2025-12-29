package com.learn_spring_security.spring_security.utils;

import com.learn_spring_security.spring_security.dto.PostDTO;
import com.learn_spring_security.spring_security.entity.PostEntity;
import com.learn_spring_security.spring_security.entity.UserEntity;
import com.learn_spring_security.spring_security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {
    private final PostService postService;

    public boolean isOwnerOfPost(Long postId) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
