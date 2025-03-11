package com.bidding.crew.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountUser accountUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return User.builder()
                .authorities("ROLE_" + accountUser.getRole().name())
                .username(username)
                .password(accountUser.getPassword())
                .build();

    }

    public List<AccountUserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(accountUser -> new AccountUserDto(
                        accountUser.getUserId(),
                        accountUser.getUsername(),
                        accountUser.getRole()))
                .toList();
    }

    public List<AccountUserDto> findUsersByRole(Role role) {
        return userRepository.findByRole(role).stream()
                .map(accountUser -> new AccountUserDto(
                        accountUser.getUserId(),
                        accountUser.getUsername(),
                        accountUser.getRole()
                ))
                .toList();
    }
}


