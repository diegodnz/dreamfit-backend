package com.dreamfitbackend.domain.user_measures;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMeasuresRepository extends JpaRepository<UserMeasures, Long> {

}
