-- Employee Table
CREATE TABLE employee (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          salary DOUBLE PRECISION NOT NULL,
                          hire_date DATE NOT NULL,
                          department_id BIGINT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department(id)
);