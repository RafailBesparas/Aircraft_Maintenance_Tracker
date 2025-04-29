CREATE TABLE maintenance_task (
    id SERIAL PRIMARY KEY,
    aircraft_id INT REFERENCES aircraft(id),
    task_description VARCHAR(255) NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL
);