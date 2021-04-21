package com.dreamfitbackend.domain.exercise;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
	@Query(value = "SELECT * FROM exercises WHERE user_id = :user_id AND type = :type ORDER BY id", nativeQuery = true)
	List<Exercise> getByUserAndType(@Param("user_id") Long userId, @Param("type") Integer type);
	
	@Query(value = "DELETE FROM exercises WHERE user_id = :user_id AND type = :type", nativeQuery = true)
	@Modifying@Transactional
	void deleteByUserAndType(@Param("user_id") Long userId, @Param("type") Integer type);
}
