CREATE TABLE usuarios(
	id BIGSERIAL NOT NULL,
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
	weight NUMERIC(5,2),
	arm_measurement NUMERIC(5,2),
	leg_measurement NUMERIC(5,2),
	hip_measurement NUMERIC(5,2),
	belly_measurement NUMERIC(5,2),
	PRIMARY KEY (id)
);

INSERT INTO usuarios (uuid, cpf, email, name, birth_date, gender, password, role_user, phone, profile_picture, weight, arm_measurement, leg_measurement, hip_measurement, belly_measurement)
	VALUES ('fc5979df-b02b-423b-88a3-10f418183b56', '33367174777', 'adddf@cin.ufpe.br', 'Alisson Diego Diniz', '1999-10-14', 'Masculino', '$2a$10$wOZPXHRzR1rHSKuZgK9q3en8h/2L6/rYxNFEBveAmfxUeMKZZhV8G',
	20, '81 984644165', 'profile_pictures/1.jpg', 76.5,	10.55, 35.32, 112.585, 14)

	

	