package com.dreamfitbackend.domain.user_rewards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRewardsRepository extends JpaRepository<UserRewards, Long> {

}
