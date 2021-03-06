CREATE TABLE exercises(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	type INTEGER NOT NULL,
	name VARCHAR(64) NOT NULL,
	reps VARCHAR(64) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);