
CREATE TABLE users (
    username TEXT PRIMARY KEY,
    password TEXT NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE polls (
    id INTEGER PRIMARY KEY,
    question TEXT
);

CREATE TABLE poll_options (
    pollId INTEGER,
    no INTEGER,
    content TEXT,
    PRIMARY KEY (pollId, no),
    FOREIGN KEY (pollId) REFERENCES polls(id)
);

CREATE TABLE poll_responses (
    username TEXT,
    pollId INTEGER,
    no INTEGER,
    post_time INTEGER,
    PRIMARY KEY (username, pollId, no),
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (pollId) REFERENCES polls(id),
    FOREIGN KEY (pollId, no) REFERENCES poll_options(pollId, no)
);

CREATE TABLE poll_comments (
    id INTEGER PRIMARY KEY,
    username TEXT,
    pollId INTEGER,
    post_time INTEGER,
    content TEXT,
    FOREIGN KEY (username) REFERENCES users(username),
    FOREIGN KEY (pollId) REFERENCES polls(id)
);