package com.bidding.crew.admin;

import com.bidding.crew.general.ResourceNotFoundException;
import com.bidding.crew.user.AccountUser;
import com.bidding.crew.user.AccountUserDto;
import com.bidding.crew.user.Role;
import com.bidding.crew.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AdminResponseDto assignRole(Long userId, Role role) {
        AccountUser user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with ID: " + userId ));
        Role previousRole = user.getRole();
        user.setRole(role);
        userRepository.save(user);

        return new AdminResponseDto(
                user.getUserId(),
                user.getUsername(),
                "Role Assigned",
                "Role changed from " + (previousRole != null ? previousRole.name() : "NONE") + " to " + role.name(),
                LocalDateTime.now()
        );
    }

    public AdminResponseDto removeRole(Long userId) {
        AccountUser user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        Role previousRole = user.getRole();
        user.setRole(null);
        userRepository.save(user);

        return new AdminResponseDto(
                user.getUserId(),
                user.getUsername(),
                "Role Removed",
                "Role " + previousRole + " successfully removed from user",
                LocalDateTime.now()
        );
    }

    public List<AccountUserDto> getUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(user -> new AccountUserDto(user.getUserId(), user.getUsername(), user.getRole()))
                .toList();
    }
}
