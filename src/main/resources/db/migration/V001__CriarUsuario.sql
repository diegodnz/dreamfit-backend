CREATE TABLE users(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	uuid VARCHAR(255) UNIQUE NOT NULL,
	cpf VARCHAR(11) UNIQUE NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	name VARCHAR(255) NOT NULL,
	birth_date DATE NOT NULL,
	gender VARCHAR(255) NOT NULL,
	password VARCHAR(255),
	role_user INTEGER NOT NULL,
	phone VARCHAR(32) NOT NULL,
	profile_picture VARCHAR(255),
	fitcoins INTEGER NOT NULL,
	token_reset VARCHAR(255)
);

CREATE TABLE user_measures(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	user_id BIGINT NOT NULL,
	date TIMESTAMP NOT NULL,
	weight NUMERIC(5,2),
	arm_measurement NUMERIC(5,2),
	leg_measurement NUMERIC(5,2),
	hip_measurement NUMERIC(5,2),
	belly_measurement NUMERIC(5,2),
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO users (uuid, cpf, email, name, birth_date, gender, password, role_user, phone, profile_picture, fitcoins)
	VALUES ('fc5979df-b02b-423b-88a3-10f418183b56', '33367174777', 'adddf@cin.ufpe.br', 'Alisson Diego Diniz', '1999-10-14', 'Masculino', '$2a$10$wOZPXHRzR1rHSKuZgK9q3en8h/2L6/rYxNFEBveAmfxUeMKZZhV8G',
		    20, '81 984644165', 'profile_pictures/1.jpg', 0),
		    
		   ('ag5979df-b02b-423b-88a3-10f418183b56', '87803418880', 'pgsa@cin.ufpe.br', 'Pedro Albuquerque', '2000-11-02', 'Feminino', '$2a$10$wOZPXHRzR1rHSKuZgK9q3en8h/2L6/rYxNFEBveAmfxUeMKZZhV8G',
		    40, '81 984644165', 'profile_pictures/2.jpg', 0),
		    
		   ('di5979df-b02b-423b-88a3-10f418183b56', '07216234251', 'diegodiniz991@gmail.com', 'Diego Diniz', '1998-01-14', 'Masculino', '$2a$10$wOZPXHRzR1rHSKuZgK9q3en8h/2L6/rYxNFEBveAmfxUeMKZZhV8G',
		    60, '81 984644165', 'profile_pictures/3.jpg', 0);
	
INSERT INTO user_measures (user_id, date, weight, arm_measurement, leg_measurement, hip_measurement, belly_measurement)
	VALUES (1, '2021-04-21 04:05:06', 76.5, 10.55, 35.32, 112.585, 14),
		   (2, '2021-04-21 04:05:06', 76.5, 10.55, 35.32, 112.585, 14),
		   (3, '2021-04-21 04:05:06', 76.5, 10.55, 35.32, 112.585, 14);

	

	