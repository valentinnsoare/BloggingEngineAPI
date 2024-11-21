package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.AuthorDto;
import io.valentinsoare.newsoutletapis.dto.PostDto;
import io.valentinsoare.newsoutletapis.entity.Author;
import io.valentinsoare.newsoutletapis.entity.Post;
import io.valentinsoare.newsoutletapis.exception.BloggingEngineException;
import io.valentinsoare.newsoutletapis.exception.ResourceNotFoundException;
import io.valentinsoare.newsoutletapis.repository.AuthorRepository;
import io.valentinsoare.newsoutletapis.repository.PostRepository;
import io.valentinsoare.newsoutletapis.utilities.AuxiliaryMethods;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final AuxiliaryMethods auxiliaryMethods;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             ModelMapper modelMapper,
                             AuxiliaryMethods auxiliaryMethods,
                             PostRepository postRepository) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
        this.auxiliaryMethods = auxiliaryMethods;
        this.postRepository = postRepository;
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public AuthorDto getAuthorByFirstName(String firstName) {
        log.info("Searching author with first name: {}.", firstName);

        Author foundAuthor = authorRepository.findByFirstName(firstName)
                .orElseThrow(() -> {
                    log.error("Author with first name {} not found.", firstName);
                    return new ResourceNotFoundException("author", Map.of("first name", firstName));
                });
        log.info("Fetched author with first name {} and id {} found.", firstName, foundAuthor.getId());

        AuthorDto newAuthorFound = modelMapper.map(foundAuthor, AuthorDto.class);
        log.info("Mapped author with first name {} to authorDto.", firstName);

        log.info("Returning author {}.", newAuthorFound);
        return newAuthorFound;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorByLastName(String lastName) {
        log.info("Searching author with last name: {}.", lastName);

        Author foundAuthor = authorRepository.findByLastName(lastName)
                .orElseThrow(() -> {
                    log.error("Author with last name {} not found.", lastName);
                    return new ResourceNotFoundException("author", Map.of("last name", lastName));
                });
        log.info("Fetched author with last name {} and id {} found.", lastName, foundAuthor.getId());

        AuthorDto newAuthorFound = modelMapper.map(foundAuthor, AuthorDto.class);
        log.info("Mapped author with last name {} to authorDto.", lastName);

        log.info("Returning author {}.", newAuthorFound);
        return newAuthorFound;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.info("Checking if author with email {} exists.", email);

        if (Boolean.TRUE.equals(authorRepository.existsByEmail(email))) {
            log.info("Author with email {} exists.", email);
            return true;
        }

        log.info("Author with email {} does not exist.", email);
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAuthorsByIds(List<Long> userIds) {
        log.info("Searching for authors with ids: {}.", userIds);

        List<Author> byIdIn = authorRepository.findByIdIn(userIds);

        if (!byIdIn.isEmpty()) {
            log.info("Found authors with ids: {}.", userIds);

            return byIdIn.stream()
                    .map(author -> modelMapper.map(author, AuthorDto.class))
                    .toList();
        }

        log.info("Authors with ids {} not found.", userIds);
        return List.of();
    }

    @Override
    @Transactional
    public AuthorDto createAuthor(AuthorDto authorDto) {
        log.info("Creating author {}.", authorDto);
        Author author = modelMapper.map(authorDto, Author.class);
        String emailToBeSearch = author.getEmail();

        log.info("Checking if author with email {} exists.", emailToBeSearch);
        authorRepository.findByEmail(author.getEmail())
                .ifPresent(a -> {
                    log.error("Author with email {} already exists.", emailToBeSearch);
                    throw new BloggingEngineException("author", "email already exists", Map.of("email", emailToBeSearch));
                });

        try {
            author = authorRepository.save(author);
            log.info("Author {} created.", author);
        } catch (Exception e) {
            log.error("Error creating author {}.", authorDto);
            throw new BloggingEngineException("author", "error creating", Map.of("author", authorDto.toString()));
        }

        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        log.info("Updating author {}.", authorDto);

        Author author = modelMapper.map(authorDto, Author.class);
        String emailToBeChecked = author.getEmail();

        Author existentAuthorInDb = authorRepository.findByEmail(emailToBeChecked)
                .orElseThrow(() -> {
                    log.error("Author with email {} not found.", emailToBeChecked);
                    throw new ResourceNotFoundException("author", Map.of("email", emailToBeChecked));
                });

        existentAuthorInDb.setEmail(auxiliaryMethods.updateIfPresent(authorDto.getEmail(), existentAuthorInDb.getEmail()))
                .setFirstName(auxiliaryMethods.updateIfPresent(authorDto.getFirstName(), existentAuthorInDb.getFirstName()))
                .setLastName(auxiliaryMethods.updateIfPresent(authorDto.getLastName(), existentAuthorInDb.getLastName()));

        Author savedAuthor;

        try {
            savedAuthor = authorRepository.save(existentAuthorInDb);
        } catch (Exception e) {
            log.error("Error updating author {}.", authorDto);
            throw new BloggingEngineException("author", "error updating", Map.of("author", authorDto.toString()));
        }

        return modelMapper.map(savedAuthor, AuthorDto.class);
    }

    @Override
    @Transactional
    public List<PostDto> updateAuthorPostList(AuthorDto author, List<Long> postIds) {
        log.info("Updating author {} post list with post ids: {}.", author, postIds);
        String searchedEmail = author.getEmail();

        Author searchedAuthorByEmail = authorRepository.findByEmail(searchedEmail)
                .orElseThrow(() -> {
                    log.error("Author with email {} not found.", searchedEmail);
                    throw new ResourceNotFoundException("author", Map.of("email", searchedEmail));
                });

        Set<Post> listWithPosts = new HashSet<>();

        for (Long id : postIds) {
            Post existentPost = postRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Post with id {} not found.", id);
                        throw new ResourceNotFoundException("post", Map.of("id", id.toString()));
                    });

            log.info("Post with id {} found.", id);
            listWithPosts.add(existentPost);
        }

        searchedAuthorByEmail.setAllPosts(listWithPosts);
        log.info("Author {} post list updated with post ids: {}.", author, postIds);

        try {
            authorRepository.save(searchedAuthorByEmail);
            log.info("Author {} post list updated with post ids: {}.", author, postIds);
        } catch (Exception e) {
            log.error("Error updating author {} post list with post ids: {}.", author, postIds);
            throw new BloggingEngineException("author", "error updating post list", Map.of("author", author.toString()));
        }

        List<PostDto> authorAllPostsUpdates = new ArrayList<>();

        listWithPosts.forEach(post -> {
            PostDto newPost = modelMapper.map(post, PostDto.class);
            authorAllPostsUpdates.add(newPost);
        });

        return authorAllPostsUpdates;
    }

    @Override
    public void deleteAuthor(Long id) {

    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        return List.of();
    }

    @Override
    public Long countAuthors() {
        return 0L;
    }
}
