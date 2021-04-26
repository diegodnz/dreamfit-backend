package com.dreamfitbackend.domain.user_rewards;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRewardsRepository extends JpaRepository<UserRewards, Long> {
	
	@Query(value = "SELECT * FROM user_rewards WHERE user_id = :user_id AND reward_id = :reward_id AND delivered = false LIMIT 1", nativeQuery = true)
	UserRewards findByUserAndReward(@Param("user_id") Long user_id, @Param("reward_id") Long reward_id);
	
	@Query(value = "UPDATE user_rewards SET delivered = true WHERE user_id = :user_id AND reward_id = :reward_id", nativeQuery = true)
	@Modifying@Transactional
	void deliverByUserAndReward(@Param("user_id") Long user_id, @Param("reward_id") Long reward_id);

}
