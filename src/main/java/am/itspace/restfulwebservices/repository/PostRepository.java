package am.itspace.restfulwebservices.repository;

import am.itspace.restfulwebservices.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUserId(int id);
}
