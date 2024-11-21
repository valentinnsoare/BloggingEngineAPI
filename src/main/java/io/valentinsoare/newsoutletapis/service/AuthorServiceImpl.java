package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.AuthorDto;
import io.valentinsoare.newsoutletapis.dto.PostDto;
import io.valentinsoare.newsoutletapis.entity.Author;
import io.valentinsoare.newsoutletapis.exception.ResourceNotFoundException;
import io.valentinsoare.newsoutletapis.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Long id) {
        log.info("Searching author with id: {}.", id);

        Author foundAuthor = authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Author with id {} not found.", id);
                    return new ResourceNotFoundException("author", Map.of("id", id.toString()));
                });

        AuthorDto newAuthorFound = modelMapper.map(foundAuthor, AuthorDto.class);

        foundAuthor.getAllPosts().forEach(post -> {
            PostDto newPost = modelMapper.map(post, PostDto.class);
            newAuthorFound.getPostsFromAuthor().add(newPost);
        });

        log.info("Fetched author with id {} found.", id);
        return newAuthorFound;
    }

    @Override
    public AuthorDto getAuthorByEmail(String email) {
        log.info("Searching author with email: {}.", email);

        Author foundAuthor = authorRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Author with email {} not found.", email);
                    return new ResourceNotFoundException("author", Map.of("email", email));
                });

        AuthorDto newAuthorFound = modelMapper.map(foundAuthor, AuthorDto.class);

        foundAuthor.getAllPosts().forEach(post -> {
            PostDto newPost = modelMapper.map(post, PostDto.class);
            newAuthorFound.getPostsFromAuthor().add(newPost);
        });

        log.info("Fetched author with email {} found.", email);
        return newAuthorFound;
    }

    @Override
    public AuthorDto getAuthorByFirstName(String firstName) {
        return null;
    }

    @Override
    public AuthorDto getAuthorByLastName(String lastName) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public List<AuthorDto> getAuthorsByIds(List<Long> userIds) {
        return List.of();
    }
}
