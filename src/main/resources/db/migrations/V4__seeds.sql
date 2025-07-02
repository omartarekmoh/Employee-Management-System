-- Seed Departments
INSERT INTO department (name) VALUES
                                  ('Engineering'),
                                  ('Marketing'),
                                  ('Sales'),
                                  ('Finance'),
                                  ('HR');

-- Seed Employees
INSERT INTO employee (name, email, salary, hire_date, department_id) VALUES
                                                                         ('Alice Smith', 'alice@company.com', 65000, CURRENT_DATE, 1),
                                                                         ('Bob Johnson', 'bob@company.com', 62000, CURRENT_DATE, 2),
                                                                         ('Charlie Lee', 'charlie@company.com', 58000, CURRENT_DATE, 3),
                                                                         ('Diana King', 'diana@company.com', 70000, CURRENT_DATE, 4),
                                                                         ('Ethan Brown', 'ethan@company.com', 61000, CURRENT_DATE, 5);

-- Seed Users
INSERT INTO users (username, email, password, role) VALUES
                                                        ('admin', 'admin@company.com', '$2a$12$YGIQsajODZHpJZKjV2Ii1OpxTrjQ3x/7bYi/.qm1Jz7L1B7ZQtToW', 'ADMIN'), -- password: admin123
                                                        ('user', 'user@company.com', '$2a$12$wQJLzwJSNPmbJL8u/NyuK.rRKhvlcxrQpjc1M.vWVg/XEwLbL08iq', 'USER'); -- password: user123
