package com.bidding.crew.user;

public class AccountUserDto {
    private Long id;
    private String username;
    private Role role;

    public AccountUserDto() {}

    public AccountUserDto(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "AccountUserDto{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
