package com.bidding.crew.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminResponseDto {
    private Long userId;
    private String username;
    private String action;
    private String message;
    private LocalDateTime timestamp;
}
