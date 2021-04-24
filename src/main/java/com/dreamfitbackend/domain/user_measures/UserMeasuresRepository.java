package com.dreamfitbackend.domain.user_measures;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMeasuresRepository extends JpaRepository<UserMeasures, Long> {
	
	@Query(value = "SELECT weight FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 2", nativeQuery = true)
	List<Float> getWeightMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT arm_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 2", nativeQuery = true)
	List<Float> getArmMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT leg_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 2", nativeQuery = true)
	List<Float> getLegMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT hip_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 2", nativeQuery = true)
	List<Float> getHipMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT belly_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 2", nativeQuery = true)
	List<Float> getBellyMeasures(@Param("id") Long id);
	
	@Query(value = "SELECT weight FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 1", nativeQuery = true)
	Float getWeight(@Param("id") Long id);
	
	@Query(value = "SELECT arm_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 1", nativeQuery = true)
	Float getArmMeasurement(@Param("id") Long id);
	
	@Query(value = "SELECT leg_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 1", nativeQuery = true)
	Float getLegMeasurement(@Param("id") Long id);
	
	@Query(value = "SELECT hip_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 1", nativeQuery = true)
	Float getHipMeasurement(@Param("id") Long id);
	
	@Query(value = "SELECT belly_measurement FROM user_measures WHERE user_id = :id ORDER BY date DESC limit 1", nativeQuery = true)
	Float getBellyMeasurement(@Param("id") Long id);

}
