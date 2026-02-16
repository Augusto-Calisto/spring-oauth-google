package br.com.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.tech.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findBySubAndRegistrationId(String sub, String registrationId);
}