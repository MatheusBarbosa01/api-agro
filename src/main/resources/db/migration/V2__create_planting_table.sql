CREATE TABLE plantings (
    id SERIAL PRIMARY KEY,
    crop_type VARCHAR(100) NOT NULL,
    seed_type VARCHAR(100),
    soil_type VARCHAR(100),
    city VARCHAR(100),
    land_size DOUBLE PRECISION,
    date DATE,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_planting_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);