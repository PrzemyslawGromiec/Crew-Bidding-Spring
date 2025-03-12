package com.bidding.crew.general;

import com.bidding.crew.report.InvalidPeriodException;
import com.bidding.crew.report.InvalidReportStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return generateHandlingException(ex,HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return generateHandlingException(ex,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidReportStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReportStateException(InvalidReportStateException ex) {
        return generateHandlingException(ex,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DatabaseConnectionException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseConnectionException(DatabaseConnectionException ex) {
        return generateHandlingException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidPeriodException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPeriodException(InvalidPeriodException ex) {
        return generateHandlingException(ex,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoFlightSuggestionsException.class)
    public ResponseEntity<ErrorResponse> handleNoFlightSuggestionsException(NoFlightSuggestionsException ex) {
        return generateHandlingException(ex,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFlightDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFlightDataException(InvalidFlightDataException ex) {
       return generateHandlingException(ex,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorDetails = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getParameterName(), "Required parameter is missing");

        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Missing request parameter",
                LocalDateTime.now(),
                errors
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String parameterName = ex.getName();
        String providedValue = (ex.getValue() != null) ? ex.getValue().toString() : "null";
        String expectedType = (ex.getRequiredType() != null) ? ex.getRequiredType().getSimpleName() : "unknown type";
        String errorMessage = "Invalid value '" + providedValue + "' for parameter '" +
                parameterName + "'. Expected type: " + expectedType;
        return new ResponseEntity<>(new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage,
                LocalDateTime.now(),
                null
        ), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> generateHandlingException(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(status.value(), ex.getMessage(), LocalDateTime.now(), null),status);
    }
}

/* Examples of exceptions that need field errors:
* BindException
* ConstraintViolationException
* HttpMessageNotReadableException
* TypeMismatchException
* MissingServletRequestParameterException
* MissingPathVariableException
* */
