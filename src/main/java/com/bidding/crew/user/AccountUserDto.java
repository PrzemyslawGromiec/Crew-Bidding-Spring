package com.bidding.crew.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUserDto {
    private Long id;
    private String username;
    private Role role;
}
