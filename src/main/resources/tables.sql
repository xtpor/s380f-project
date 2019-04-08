
CREATE TABLE users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE t2 (
    field1 TEXT,
    field2 INTEGER
);