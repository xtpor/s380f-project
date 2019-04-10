
CREATE TABLE users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE lecture (
    lid INTEGER PRIMARY KEY,
    title TEXT NOT NULL
);

CREATE TABLE attachment (
    aid INTEGER PRIMARY KEY,
    lid INTEGER NOT NULL,
    filename TEXT NOT NULL,
    filetype TEXT NOT NULL,
    content BLOB NOT NULL
);

CREATE TABLE comment (
    cid INTEGER PRIMARY KEY,
    lid INTEGER NOT NULL,
    username TEXT NOT NULL,
    comment TEXT NOT NULL
);
