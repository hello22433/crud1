package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "post-list";  // post-list.html로 이동
    }

    @GetMapping("/new")
    public String showPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post-form";  // post-form.html로 이동
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post) {
        postService.createPost(post);
        return "redirect:/posts";  // 게시글 목록으로 리디렉션
    }

    @GetMapping("/{id}")
    public String getPostDetail(@PathVariable Long id, Model model) {
        postService.getPost(id).ifPresent(post -> model.addAttribute("post", post));
        return "post-detail";  // post-detail.html로 이동
    }

    @PostMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";  // 삭제 후 목록으로 리디렉션
    }

    // 🔧 수정 폼 보여주기
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        model.addAttribute("post", post);
        return "post-edit-form"; // 수정 폼으로 이동
    }

    // 🔧 수정 처리
    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post updatedPost) {
        postService.updatePost(id, updatedPost);
        return "redirect:/posts";
    }

}
