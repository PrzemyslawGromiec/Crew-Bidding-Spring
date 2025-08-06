package com.bidding.crew;

import com.bidding.crew.admin.AdminResponseDto;
import com.bidding.crew.admin.AdminService;
import com.bidding.crew.general.ResourceNotFoundException;
import com.bidding.crew.user.AccountUser;
import com.bidding.crew.user.Role;
import com.bidding.crew.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    private AccountUser user;

    @BeforeEach
    void setup() {
        user = new AccountUser();
        user.setUserId(1L);
        user.setUsername("testUser");
        user.setRole(Role.USER);
    }

    @Test
    void shouldAssignNewRoleAndReturnCorrectResponse() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        AdminResponseDto response = adminService.assignRole(1L,Role.ADMIN);

        //then
        assertEquals("testUser", response.getUsername());
        assertEquals("Role assigned", response.getAction());
        assertTrue(response.getMessage().contains("changed from USER to ADMIN"));
        assertNotNull(response.getTimestamp());
    }

    @Test
    void shouldRemovePreviousRoleAndReturnCorrectResponse() {
        // given
        user.setRole(Role.ADMIN);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(AccountUser.class))).thenReturn(user);

        // when
        AdminResponseDto response = adminService.removeRole(1L);

        // then
        assertEquals(1L, response.getUserId());
        assertEquals("testUser", response.getUsername());
        assertEquals("Role Removed", response.getAction());
        assertTrue(response.getMessage().contains("Role ADMIN successfully removed"));
        assertNotNull(response.getTimestamp());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnAssignRole() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                adminService.assignRole(999L, Role.ADMIN));

        assertEquals("User not found with ID: 999", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundOnRemoveRole() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                adminService.removeRole(999L));

        assertEquals("User not found with ID: 999", ex.getMessage());
    }

    @Test
    void shouldCallSaveMethodOnAssignRole() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        adminService.assignRole(1L, Role.ADMIN);

        verify(userRepository).save(user);
    }

    @Test
    void shouldReturnUsersWithGivenRole() {
        List<AccountUser> users = List.of(user);
        when(userRepository.findByRole(Role.USER)).thenReturn(users);

        var result = adminService.getUsersByRole(Role.USER);

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
        assertEquals(Role.USER, result.get(0).getRole());
    }
}
