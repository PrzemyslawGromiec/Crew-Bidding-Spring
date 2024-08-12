package com.bidding.crew.general;

import com.bidding.crew.report.InvalidPeriodException;
import com.bidding.crew.report.InvalidReportStateException;
import com.bidding.crew.flight.AircraftType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return generateHandlingException(ex,HttpStatus.NOT_FOUND);
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

        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                LocalDateTime.now());
        errorDetails.setErrors(errors);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> generateHandlingException(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(status.value(), ex.getMessage(), LocalDateTime.now()),status);
    }

}
