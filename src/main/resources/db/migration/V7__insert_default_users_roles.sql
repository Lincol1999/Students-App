INSERT INTO student.users_roles (user_id, role_id) VALUES(
                 (select user_id from student.users where username = 'admin'),
                 (select role_id from student.roles where role_name = 'ROLE_ADMIN')
);

INSERT INTO student.users_roles (user_id, role_id) VALUES(
                  (select user_id from student.users where username = 'user'),
                  (select role_id from student.roles where role_name = 'ROLE_USER')
);