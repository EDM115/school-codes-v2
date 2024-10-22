CREATE DATABASE "location_db";

\c location_db

CREATE TABLE "location" (
    id SERIAL PRIMARY KEY,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    user_id INTEGER NOT NULL
);
