package com.bidding.crew.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "2. User Management",
        description = "Endpoints related to retrieving and filtering users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(
            summary = "Get all users",
            description = "Returns a list of all registered users in the system"
    )
    public List<AccountUserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/search")
    @Operation(
            summary = "Search users by role",
            description = "Filters and returns users matching the specified role (e.g. ADMIN or USER)"
    )
    public List<AccountUserDto> findByRole(@RequestParam Role role) {
        return userService.findUsersByRole(role);
    }
}
