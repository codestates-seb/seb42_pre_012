package com.pre012.server.advice;

import javax.validation.ConstraintViolationException;

import com.pre012.server.common.dto.SingleResponseDto;
import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
     @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(e.getBindingResult());

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(e.getExceptionCode());

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.BAD_REQUEST);
    }

}
