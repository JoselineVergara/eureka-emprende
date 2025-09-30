package com.example.eureka.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ResponseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleAllException(Exception ex, WebRequest request){
        log.error("error", ex);
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> handleAllRuntimeException(Exception ex, WebRequest request){
        log.error("error", ex);
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//	@ExceptionHandler(ModelNotFoundException.class)
//	public ResponseEntity<CustomErrorResponse> handleModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
//		CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
//	}

//    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField().concat(":").concat(e.getDefaultMessage())
                ).collect(Collectors.joining(","));

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(Exception ex, WebRequest request){
        log.error("error", ex);
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomExceptionValidations.class)
    public ResponseEntity<CustomErrorResponse> handleValidacionesException(Exception ex, WebRequest request){
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

//    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable mostSpecificCause = ex.getMostSpecificCause();
        if (mostSpecificCause instanceof DateTimeParseException) {
            CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), "Formato de fecha inválido. Use el formato ISO 'yyyy-MM-dd'T'HH:mm:ss'.", request.getDescription(false));
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), "Error en la solicitud: " + ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
