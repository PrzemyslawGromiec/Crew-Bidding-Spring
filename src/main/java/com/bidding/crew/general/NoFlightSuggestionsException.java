package com.bidding.crew.general;

public class NoFlightSuggestionsException extends RuntimeException {

    public NoFlightSuggestionsException(String message) {
        super(message);
    }

    public NoFlightSuggestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
