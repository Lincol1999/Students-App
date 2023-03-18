CREATE TABLE IF NOT EXISTS public.student (
                                 id int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
                                 "name" varchar NULL,
                                 surname varchar NULL,
                                 age int4 NULL,
                                 active bool NULL,
                                 CONSTRAINT student_pk PRIMARY KEY (id)
);