package com.dreamfitbackend.domain.gymclass;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
	Class getById(Long id);
	
	@Query(value = "SELECT COUNT(*)"
			+ "FROM students_classes as relation INNER JOIN classes as class ON class.id = relation.class_id "
			+ "WHERE relation.user_id = :user_id AND DATE(class.date) = :currentDate;", // TODO investigar data
			nativeQuery = true)
	Integer getUserClasses(@Param("user_id") Long userId, @Param("currentDate") Date currentDate);
	
	@Query(value = "SELECT (SELECT class FROM classes as class) "
			+ "FROM students_classes as relation "
			+ "WHERE user_id = :user_id and class_id = :class_id",
			nativeQuery = true)
	Class verifyRelation(@Param("user_id") Long userId, @Param("class_id") Long classId);
}
