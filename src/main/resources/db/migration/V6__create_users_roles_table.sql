CREATE TABLE IF NOT EXISTS public.users_roles (

    id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id int4 NOT NULL,
    role_id int4 NOT NULL,
    CONSTRAINT users_roles_pk PRIMARY KEY (id),
    CONSTRAINT users_roles_fk FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT users_roles_fk_1 FOREIGN KEY (role_id) REFERENCES public.roles(role_id)
    );