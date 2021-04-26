package com.dreamfitbackend.domain.user_rewards;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dreamfitbackend.domain.rewards.Reward;

@Repository
public interface UserRewardsRepository extends JpaRepository<UserRewards, Long> {

}
