package com.dreamfitbackend.domain.gymclass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
	Class getById(Long id);
	
	@Query(value = "SELECT COUNT(*)"
			+ "FROM students_classes as relation INNER JOIN classes as class ON class.id = relation.class_id "
			+ "WHERE relation.user_id = :user_id AND class.start_date >= :start_day AND class.start_date < :end_day",
			nativeQuery = true)
	Integer getUserClasses(@Param("user_id") Long userId, @Param("start_day") LocalDateTime startDay, @Param("end_day") LocalDateTime endDay);
	
	@Query(value = "SELECT COUNT(*)"
			+ "FROM classes "
			+ "WHERE class.start_date >= :start_day AND class.start_date < :end_day",
			nativeQuery = true)
	List<Class> getByDate(@Param("start_day") LocalDateTime startDay, @Param("end_day") LocalDateTime endDay);
	
	@Query(value = "SELECT class_id "
			+ "FROM students_classes "
			+ "WHERE user_id = :user_id and class_id = :class_id",
			nativeQuery = true)
	Long verifyRelation(@Param("user_id") Long userId, @Param("class_id") Long classId);
}
