CREATE TABLE posts(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	description VARCHAR(255),
	picture VARCHAR(255),
	likes INTEGER NOT NULL,
	emotes INTEGER NOT NULL,
	arms INTEGER NOT NULL,
	time TIMESTAMP NOT NULL
);

CREATE TABLE post_interactions(
	user_id BIGINT NOT NULL,
	post_id BIGINT NOT NULL,	
	interaction INTEGER NOT NULL,
	PRIMARY KEY(user_id, post_id, interaction)
)
