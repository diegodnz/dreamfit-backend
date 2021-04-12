CREATE TABLE classes(
	id BIGSERIAL NOT NULL PRIMARY KEY,
	class_name VARCHAR(255) NOT NULL,
	date TIMESTAMP NOT NULL,
	filled_vacancies INTEGER NOT NULL,
	total_vacancies INTEGER NOT NULL
);

CREATE TABLE teachers_classes(
	class_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	FOREIGN KEY (class_id) REFERENCES classes (id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	PRIMARY KEY(class_id, user_id)
);

CREATE TABLE students_classes(
	class_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	FOREIGN KEY (class_id) REFERENCES classes (id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
	PRIMARY KEY(class_id, user_id)
);