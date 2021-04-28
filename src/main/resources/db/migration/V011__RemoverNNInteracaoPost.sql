DROP TABLE post_interactions;

CREATE TABLE post_interactions(
	user_id BIGINT NOT NULL,
	post_id BIGINT NOT NULL,	
	interaction INTEGER,
	PRIMARY KEY(user_id, post_id)
)