package ru.netology.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping("/get/all")
  public List<Post> all() {
    return service.all();
  }

  @GetMapping("/get/{id}")
  public Post getById(@PathVariable long id)  {
    return service.getById(id);
  }

  @PostMapping("/posts")
  public Post save(@RequestBody Post post)  {
    return service.save(post);
  }

  @DeleteMapping("/delete/{id}")
  public Post removeById(@PathVariable long id) throws IOException {
    return service.removeById(id);
  }
}
