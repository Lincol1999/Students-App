CREATE TABLE IF NOT EXISTS public.users (
    user_id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
    username varchar(10) NOT NULL,
    "password" varchar(100) NOT NULL,
    email varchar(100) NULL,
    firstname varchar(30) NULL,
    lastname varchar(30) NULL,
    CONSTRAINT users_pk PRIMARY KEY (user_id),
    CONSTRAINT users_un UNIQUE (username, email)
);