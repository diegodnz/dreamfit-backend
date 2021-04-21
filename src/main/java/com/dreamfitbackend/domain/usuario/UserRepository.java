package com.dreamfitbackend.domain.usuario;

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
	
	@Query(value = "SELECT * FROM users WHERE role_user = :role AND name ilike '%'||:name||'%'", nativeQuery = true)
	List<User> findAllByNameAndRole(@Param("name") String name, @Param("role") Integer role);
	
	@Query(value = "SELECT * FROM users WHERE role_user = :role AND cpf = :cpf", nativeQuery = true)
	List<User> findAllByCpfAndRole(@Param("cpf") String cpf, @Param("role") Integer role);
	
	@Query(value = "SELECT * FROM users WHERE role_user = :role AND email = :email", nativeQuery = true)
	List<User> findAllByEmailAndRole(@Param("email") String email, @Param("role") Integer role);
	
	@Query(value = "UPDATE users SET token_reset = :token WHERE email = :email", nativeQuery = true)
	@Modifying@Transactional
	void setResetToken(@Param("email") String email, @Param("token") String token_reset);
	
	@Query(value = "SELECT weight FROM user_measures WHERE user_id = :id ORDER BY date limit 2", nativeQuery = true)
	List<Float> getWeightMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT arm_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 2", nativeQuery = true)
	List<Float> getArmMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT leg_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 2", nativeQuery = true)
	List<Float> getLegMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT hip_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 2", nativeQuery = true)
	List<Float> getHipMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT belly_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 2", nativeQuery = true)
	List<Float> getBellyMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT weight FROM user_measures WHERE user_id = :id ORDER BY date limit 1", nativeQuery = true)
	Float getWeight(@Param("id") Long id);
	
	@Query(value = "SELECT arm_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 1", nativeQuery = true)
	Float getArmMeasurement(@Param("id") Long id);
	
	@Query(value = "SELECT leg_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 1", nativeQuery = true)
	Float getLegMeasurement(@Param("id") Long id);
	
	@Query(value = "SELECT hip_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 1", nativeQuery = true)
	Float getHipMeasurement(@Param("id") Long id);
	
	@Query(value = "SELECT belly_measurement FROM user_measures WHERE user_id = :id ORDER BY date limit 1", nativeQuery = true)
	Float getBellyMeasurement(@Param("id") Long id);
}
