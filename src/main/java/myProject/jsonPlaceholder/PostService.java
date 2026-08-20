package myProject.jsonPlaceholder;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public Post create(Post post) {
        return repo.save(post);
    }

    public List<Post> getAll() {
        return repo.findAll();
    }

    public Post getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Post update(Long id, Post updated) {
        Post post = repo.findById(id).orElse(null);
        if (post == null) return null;

        post.setTitle(updated.getTitle());
        post.setBody(updated.getBody());
        post.setUserId(updated.getUserId());

        return repo.save(post);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
