package com.bidding.crew.admin;

import com.bidding.crew.user.AccountUserDto;
import com.bidding.crew.user.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<AdminResponseDto> assignRoleToUser(@PathVariable Long userId, @RequestParam Role role) {
        return ResponseEntity.ok(adminService.assignRole(userId, role));
    }

    @DeleteMapping("/users/{userId}/role")
    public ResponseEntity<AdminResponseDto> removeRoleFromUser(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.removeRole(userId));
    }

    @GetMapping("/users")
    public ResponseEntity<List<AccountUserDto>> getUsersByRole(@RequestParam Role role) {
        return ResponseEntity.ok(adminService.getUsersByRole(role));
    }
}
