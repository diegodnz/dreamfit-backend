package com.dreamfitbackend.domain.rewards;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dreamfitbackend.domain.rewards.models.RewardOutputRedeem;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
	
	Reward getById(Long id);
	
	@Query(value = "SELECT * FROM rewards ORDER BY quantity DESC", nativeQuery = true)
	List<Reward> getRewards();
	
	@Query(value = "SELECT r.id as id, COUNT(*) as quantity FROM rewards as r WHERE r.id in (SELECT ur.reward_id FROM user_rewards as ur WHERE ur.user_id = :user_id AND ur.delivered = false) GROUP BY r.id",
			nativeQuery = true)
	List<RewardOutputRedeem> getUndeliveredRewardsByUser(@Param("user_id") Long id);

}
