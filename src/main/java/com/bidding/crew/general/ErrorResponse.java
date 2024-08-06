package com.bidding.crew.general;

import java.time.Instant;
import java.util.Date;

public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;
//      System.currentTimeMillis();
//		Instant.now().toEpochMilli();
    //  new Date().getTime();

    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(new Date().getTime());
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
