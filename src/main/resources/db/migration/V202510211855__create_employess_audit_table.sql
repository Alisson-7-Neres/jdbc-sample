CREATE TABLE employees_audit (
	id BIGINT NOT NULL AUTO_INCREMENT,
	employee_id BIGINT NOT NULL,
	old_name VARCHAR(150),
    name VARCHAR(150),
	old_salary DECIMAL(10,2),
    salary DECIMAL(10,2),
	old_birthday TIMESTAMP,
    birthday TIMESTAMP,
    operation CHAR(1),
	create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
