CREATE TABLE usuarios(
	id BIGSERIAL NOT NULL,
	uuid VARCHAR(255) UNIQUE NOT NULL,
	cpf VARCHAR(11) UNIQUE NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	name VARCHAR(255) NOT NULL,
	bith_date DATE NOT NULL,
	gender VARCHAR(255) NOT NULL,
	password VARCHAR(255),
	role_user INTEGER NOT NULL,
	phone VARCHAR(32) NOT NULL,
	profile_picture VARCHAR(255),
	weight NUMERIC(5,2),
	arm_measurement NUMERIC(5,2),
	leg_measurement NUMERIC(5,2),
	hip_measurement NUMERIC(5,2),
	belly_measurement NUMERIC(5,2),
	PRIMARY KEY (id)
);
	

	