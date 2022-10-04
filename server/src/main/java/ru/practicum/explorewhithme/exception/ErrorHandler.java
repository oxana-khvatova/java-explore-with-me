package ru.practicum.explorewhithme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        return new ErrorResponse(
                String.format("Пользователь не найден: \"%s\".", e.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final CategoryNotFoundException e) {
        return new ErrorResponse(
                String.format("Категория не найдена: \"%s\".", e.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final CommentNotFoundException e) {
        return new ErrorResponse(
                String.format("Категория не найдена: \"%s\".", e.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final EventNotFoundException e) {
        return new ErrorResponse(
                String.format("Категория не найдена: \"%s\".", e.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final DateException e) {
        return new ErrorResponse(
                "Exception: " + e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleValidationException(final NullPointerException e) {
        return new ErrorResponse(
                "Exception: " + e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleValidationException(final ArithmeticException e) {
        return new ErrorResponse(
                "Exception: " + e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleValidationException(final AccessException e) {
        return new ErrorResponse(
                "Exception: " + e.getMessage()
        );
    }
}