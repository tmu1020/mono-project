package jp.co.monocrea.exception;

import jakarta.ws.rs.core.Response;

public class ConflictException extends BusinessException {

    public ConflictException(String message) {
        super("CONFLICT", message, Response.Status.CONFLICT.getStatusCode());
    }
}