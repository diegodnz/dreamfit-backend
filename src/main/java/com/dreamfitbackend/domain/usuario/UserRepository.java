package com.dreamfitbackend.domain.usuario;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByCpf(String cpf);
	
	User findByEmail(String email);
	
	User findByUuid(String uuid);
	
	@Query(value = "SELECT * FROM users WHERE role_user = :role", nativeQuery = true)
	List<User> findAllByRole(@Param("role") Integer role);
	
	@Query(value = "UPDATE users SET token_reset = :token WHERE email = :email", nativeQuery = true)
	@Modifying@Transactional
	void setResetToken(@Param("email") String email, @Param("token") String token_reset);
}
