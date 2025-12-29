package com.learn_spring_security.spring_security.controllers;
import com.learn_spring_security.spring_security.dto.PostDTO;
import com.learn_spring_security.spring_security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured("ROLE_USER")
//    @Secured({"ROLE_USER", "POST_VIEW"})
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
//    @PreAuthorize("hasRole('USER')")
//    @PreAuthorize("hasAnyRole('USER', 'ADMIN') AND hasAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('POST_CREATE")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }

}
