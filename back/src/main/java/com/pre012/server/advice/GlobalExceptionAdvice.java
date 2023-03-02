package com.pre012.server.advice;

import com.pre012.server.common.dto.SingleResponseDto;
import com.pre012.server.exception.BusinessLogicException;
import com.pre012.server.exception.ErrorResponseDto;
import com.pre012.server.exception.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

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

    // 질문 검색 - USER 에서 user 아이디값이 숫자가 아닐 때 에러 발생
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleNumberFormatException(
            NumberFormatException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(ExceptionCode.PARAMETER_NOT_VALID);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleNullPointerException(
            NullPointerException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(ExceptionCode.PARAMETER_NOT_VALID);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleMethodNotSupportException(
            HttpRequestMethodNotSupportedException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(ExceptionCode.REQUEST_NOT_SUPPORT);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleMissingParameterException(
            MissingServletRequestParameterException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(ExceptionCode.PARAMETER_NOT_VALID);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(ExceptionCode.REQUEST_NOT_SUPPORT);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<SingleResponseDto<ErrorResponseDto>> handleSQLSyntaxErrorException(
            SQLSyntaxErrorException e) {
        final ErrorResponseDto response = ErrorResponseDto.of(ExceptionCode.PARAMETER_NOT_VALID);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.EXPECTATION_FAILED);
    }

}
