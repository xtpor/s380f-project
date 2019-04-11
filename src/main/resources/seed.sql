
INSERT INTO users VALUES ('user', 'user', false);
INSERT INTO users VALUES ('admin', 'admin', true);

INSERT INTO polls (question) VALUES ('What is your favourite course?');
INSERT INTO polls (question) VALUES ('Who do you like most?');

INSERT INTO poll_options VALUES (1, 1, '380');
INSERT INTO poll_options VALUES (1, 2, '363');
INSERT INTO poll_options VALUES (1, 3, '492');
INSERT INTO poll_options VALUES (2, 1, 'Keith');
INSERT INTO poll_options VALUES (2, 2, 'Oliver');
INSERT INTO poll_options VALUES (2, 3, 'Jeff');

INSERT INTO poll_comments (username, pollId, post_time, comment) VALUES ('user', 1, 1546300800, 'first comment from user');
INSERT INTO poll_comments (username, pollId, post_time, comment) VALUES ('admin', 1, 1546300800, 'first comment from admin');

INSERT INTO lecture VALUES(1,'hello');
INSERT INTO attachment VALUES(1,1,'test1.txt','text/plain',X'66696c652031');
INSERT INTO attachment VALUES(2,1,'test2.txt','text/plain',X'66696c652032');
INSERT INTO comment VALUES(1,1,'admin','hello');
INSERT INTO comment VALUES(2,1,'user','hi ^^');
