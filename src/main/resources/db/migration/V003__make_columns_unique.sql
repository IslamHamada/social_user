ALTER TABLE users add UNIQUE (auth0_id);
ALTER TABLE users add UNIQUE (username);
ALTER TABLE users add UNIQUE (email);