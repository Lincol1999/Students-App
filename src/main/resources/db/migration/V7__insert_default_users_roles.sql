INSERT INTO public.users_roles (user_id, role_id) VALUES(
                 (select user_id from public.users where username = 'admin'),
                 (select role_id from public.roles where role_name = 'ROLE_ADMIN')
);

INSERT INTO public.users_roles (user_id, role_id) VALUES(
                  (select user_id from public.users where username = 'user'),
                  (select role_id from public.roles where role_name = 'ROLE_USER')
);