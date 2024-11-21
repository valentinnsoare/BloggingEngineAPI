package io.valentinsoare.newsoutletapis.service;

import io.valentinsoare.newsoutletapis.dto.CommentDto;
import io.valentinsoare.newsoutletapis.response.CommentResponse;


public interface CommentService {
    CommentResponse getAllCommentsByPostId(long postId, int pageNo, int pageSize, String sortBy, String sortDir);
    CommentDto getCommentByIdAndPostId(long commentId, long postId);
    CommentDto createComment(long postId, CommentDto commentDto);
    CommentDto updateComment(long commentId, long postId, CommentDto commentDto);
    void deleteComment(long commentId, long postId);
}
