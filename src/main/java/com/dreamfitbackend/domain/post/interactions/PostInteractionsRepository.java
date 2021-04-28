package com.dreamfitbackend.domain.post.interactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInteractionsRepository extends JpaRepository<PostInteractions, Long> {
	
	@Query(value = "SELECT * FROM post_interactions WHERE user_id = :user_id AND post_id = :post_id", nativeQuery = true)
	PostInteractions findRealtion(@Param("user_id") long userId, @Param("post_id") long postId);

}
