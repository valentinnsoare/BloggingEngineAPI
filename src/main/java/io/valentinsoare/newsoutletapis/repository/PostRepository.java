package io.valentinsoare.newsoutletapis.repository;

import io.valentinsoare.newsoutletapis.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getAllByAuthor_Email(String email);
    Post getPostByTitle(String title);
    Long countPostByAuthorEmail(String email);
    List<Post> getAllByAuthorId(Long id);
    List<Post> getAllByAuthor_LastName(String firstName);
    @Query(nativeQuery = true,
            value = "SELECT * FROM post WHERE author_id IN (SELECT id FROM author WHERE email IN :authorsEmail)"
    )
    List<Post> getAllByAuthorsEmail(List<String> authorsEmail);
}
