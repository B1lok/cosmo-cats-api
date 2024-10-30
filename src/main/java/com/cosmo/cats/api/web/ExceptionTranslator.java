package com.cosmo.cats.api.web;

import com.cosmo.cats.api.service.exception.DuplicateProductNameException;
import com.cosmo.cats.api.service.exception.ProductNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product not found");
        return problemDetail;
    }

    @ExceptionHandler(DuplicateProductNameException.class)
    ProblemDetail handleDuplicateProductNameException(DuplicateProductNameException ex) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setType(URI.create("this-name-exists"));
        problemDetail.setTitle("Duplicate name");
        return problemDetail;
    }

}
