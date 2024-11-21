package io.valentinsoare.newsoutletapis.exception;

import lombok.Getter;

@Getter
public class ResourceViolationException extends RuntimeException {
    private final String msg;

    public ResourceViolationException(String msg) {
        super(String.format("Resource Violation: %s", msg));
        this.msg = msg;
    }
}
