package com.bidding.crew.admin;

import java.time.LocalDateTime;

public class AdminResponseDto {
    private Long userId;
    private String username;
    private String action;
    private String message;
    private LocalDateTime timestamp;

    public AdminResponseDto(Long userId, String username, String action, String message) {
        this.userId = userId;
        this.username = username;
        this.action = action;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
