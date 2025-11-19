package com.bidding.crew.admin;

import com.bidding.crew.user.AccountUserDto;
import com.bidding.crew.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "6. Admin", description = "Role management and admin-level user operations")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PatchMapping("/users/{userId}/role")
    @Operation(
            summary = "Assign role to user",
            description = "Grants a specific role (e.g., ADMIN or USER) to a user by ID"
    )
    public ResponseEntity<AdminResponseDto> assignRoleToUser(@PathVariable Long userId, @RequestParam Role role) {
        return ResponseEntity.ok(adminService.assignRole(userId, role));
    }

    @DeleteMapping("/users/{userId}/role")
    @Operation(
            summary = "Remove role from user",
            description = "Removes the assigned role from a user, effectively demoting them"
    )
    public ResponseEntity<AdminResponseDto> removeRoleFromUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.removeRole(userId));
    }

    @GetMapping("/users")
    @Operation(
            summary = "Get users by role",
            description = "Fetches all users filtered by a specific role"
    )
    public ResponseEntity<List<AccountUserDto>> getUsersByRole(@RequestParam Role role) {
        return ResponseEntity.ok(adminService.getUsersByRole(role));
    }
}
