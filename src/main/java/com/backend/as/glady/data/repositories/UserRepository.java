package com.backend.as.glady.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.as.glady.data.entities.Company;
import com.backend.as.glady.data.entities.User;

/**
 * @author asoilihi
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByCompany(Company company);
}
