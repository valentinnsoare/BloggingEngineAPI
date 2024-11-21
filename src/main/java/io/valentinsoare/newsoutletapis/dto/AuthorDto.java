package io.valentinsoare.newsoutletapis.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    private long id;

    @NotBlank(message = "First name is mandatory!")
    private String firstName;

    @NotBlank(message = "Last name is mandatory!")
    private String lastName;

    @NotBlank(message = "Email is mandatory!")
    @Email(message = "Email must be a valid email address!")
    private String email;

    private Set<PostDto> postsFromAuthor = Collections.emptySet();
}
