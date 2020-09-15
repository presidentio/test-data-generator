CREATE TABLE user (id bigint, email varchar, name varchar, createTime timestamp);
CREATE TABLE training (id bigint, name varchar, week int, userId bigint);
CREATE TABLE exercise (id bigint, name varchar, userId bigint, trainingId bigint);