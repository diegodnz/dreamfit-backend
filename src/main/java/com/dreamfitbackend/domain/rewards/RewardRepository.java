package com.dreamfitbackend.domain.rewards;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
	
	Reward getById(Long id);
	
	@Query(value = "SELECT * FROM rewards ORDER BY quantity DESC", nativeQuery = true)
	List<Reward> getRewards();

}
