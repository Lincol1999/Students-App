CREATE TABLE IF NOT EXISTS student.users (
                               user_id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
                               username varchar(10) NOT NULL,
                               "password" varchar(100) NOT NULL,
                               email varchar(100) NOT NULL,
                               firstname varchar(20) NULL,
                               lastname varchar(20) NULL,
                               status bool NOT NULL DEFAULT false,
                               CONSTRAINT users_pk PRIMARY KEY (user_id),
                               CONSTRAINT users_un UNIQUE (username, email)
);