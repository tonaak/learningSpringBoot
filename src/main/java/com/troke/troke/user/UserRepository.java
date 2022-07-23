package com.troke.troke.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	Page<User> findByUsernameNot(String username, Pageable page);

	List<User> findByEmail(String email);
}
