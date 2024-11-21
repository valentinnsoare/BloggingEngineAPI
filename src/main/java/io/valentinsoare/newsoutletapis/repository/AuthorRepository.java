package io.valentinsoare.newsoutletapis.repository;

import io.valentinsoare.newsoutletapis.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long id);
    Optional<Author> findByEmail(String email);
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);
    List<Author> findByIdIn(List<Long> userIds);
    Boolean existsByEmail(String email);
}
