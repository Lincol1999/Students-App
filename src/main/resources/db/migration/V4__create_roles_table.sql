CREATE TABLE IF NOT EXISTS public.roles (
                               role_id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
                               role_name varchar(15) NOT NULL,
                               status bool NOT NULL DEFAULT true,
                               CONSTRAINT roles_pk PRIMARY KEY (role_id),
                               CONSTRAINT roles_un UNIQUE (role_name)
);