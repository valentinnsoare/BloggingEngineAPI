package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.AuthorDto;
import io.valentinsoare.newsoutletapis.entity.Author;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);
    AuthorDto getAuthorByEmail(String email);
    AuthorDto getAuthorByFirstName(String firstName);
    AuthorDto getAuthorByLastName(String lastName);
    boolean existsByEmail(String email);
    List<AuthorDto> getAuthorsByIds(List<Long> userIds);
}
