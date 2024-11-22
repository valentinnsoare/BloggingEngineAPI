package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.AuthorDto;
import io.valentinsoare.newsoutletapis.dto.PostDto;
import io.valentinsoare.newsoutletapis.response.AuthorResponse;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
    AuthorDto getAuthorByEmail(String email);
    AuthorDto getAuthorByFirstName(String firstName);
    AuthorDto getAuthorByLastName(String lastName);
    boolean existsByEmail(String email);
    List<AuthorDto> getAuthorsByIds(List<Long> userIds);
    AuthorDto createAuthor(AuthorDto authorDto);
    AuthorDto updateAuthor(AuthorDto authorDto);
    List<PostDto> updateAuthorPostList(AuthorDto author, List<Long> postIds);
    void deleteAuthor(Long id);
    AuthorResponse getAllAuthors(int pageNo, int pageSize, @NotNull String sortBy, @NotNull String sortDir);
    Long countAuthors();
}
