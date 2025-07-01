CREATE TABLE daily_summary (
                               id BIGSERIAL PRIMARY KEY,
                               department VARCHAR(255) NOT NULL,
                               employee_count BIGINT NOT NULL,
                               date DATE NOT NULL
);
