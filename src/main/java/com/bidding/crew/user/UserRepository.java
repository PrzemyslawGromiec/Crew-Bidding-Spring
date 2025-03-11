package com.bidding.crew.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByUsername(String username);
    Optional<AccountUser> findByRole(Role role);
}
