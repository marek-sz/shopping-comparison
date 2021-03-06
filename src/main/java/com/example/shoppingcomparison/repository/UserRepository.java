package com.example.shoppingcomparison.repository;

import com.example.shoppingcomparison.auth.ApplicationUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUserDetails, Long> {
        ApplicationUserDetails findByUsername(String userName);
}
