package jp.co.monocrea.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        List<Map<String, String>> errors =
                exception.getConstraintViolations()
                        .stream()
                        .map(this::toError)
                        .collect(Collectors.toList());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("errors", errors))
                .build();
    }

    private Map<String, String> toError(ConstraintViolation<?> violation) {

        String field = violation.getPropertyPath().toString();

        if (field.contains(".")) {
            field = field.substring(field.lastIndexOf(".") + 1);
        }

        return Map.of(
                "field", field,
                "message", violation.getMessage()
        );
    }
}