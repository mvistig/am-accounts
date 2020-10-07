package mvi.accounts.service.util;

import mvi.accounts.service.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestErrorAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDto validationError(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorDto error = new ErrorDto();
        error.setOrigin("Accounts Rest Service");
        error.setScope("INPUT_VALIDATION");
        error.setMessage(ex.getMessage());
        return error;
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDto validationError(NoSuchElementException ex, WebRequest request) {
        ErrorDto error = new ErrorDto();
        error.setOrigin("Accounts Rest Service");
        error.setScope("ELEMENT_NOT_FOUND");
        error.setMessage(ex.getMessage());
        return error;
    }
}
