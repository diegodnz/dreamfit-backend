CREATE TABLE user_rewards (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	reward_id BIGINT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (reward_id) REFERENCES rewards (id) ON DELETE CASCADE
)