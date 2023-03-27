package br.com.escola.admin.controllers;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ErrorDTO;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EscolaControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorDTO(ex.getMessage(), "404");
    }

    @ExceptionHandler({BusinessRuleException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBusinessRuleException(BusinessRuleException ex) {
        return new ErrorDTO(ex.getMessage(), "400");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExcption(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        String status = "status:";
        String title = "title";
        String code = String.valueOf(HttpStatus.BAD_REQUEST.value());
        String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        errors.put(title, reason);
        errors.put(status, code);

        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
