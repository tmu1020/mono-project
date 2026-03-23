package jp.co.monocrea.exception;

import jakarta.ws.rs.core.Response;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super("NOT_FOUND", message, Response.Status.NOT_FOUND.getStatusCode());
    }
}