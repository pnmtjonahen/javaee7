-- admin@example.com, jack@example.com and robert@example.com, with password, MD5('1234')

INSERT INTO "USERS" (USERID, PASSWORD) VALUES ('robert@example.com','81dc9bdb52d04dc20036dbd8313ed055');
INSERT INTO "USERS" (USERID, PASSWORD) VALUES ('jack@example.com'  ,'81dc9bdb52d04dc20036dbd8313ed055');
INSERT INTO "USERS" (USERID, PASSWORD) VALUES ('admin@example.com' ,'81dc9bdb52d04dc20036dbd8313ed055');

-- 2 groups, normal users and admins
INSERT INTO "GROUPS" (GROUPID) VALUES ('USERS');
INSERT INTO "GROUPS" (GROUPID) VALUES ('ADMIN');

-- user to group mapping
INSERT INTO "USERS_GROUPS" (GROUPID, USERID) VALUES ('USERS','robert@example.com');
INSERT INTO "USERS_GROUPS" (GROUPID, USERID) VALUES ('USERS','jack@example.com');
INSERT INTO "USERS_GROUPS" (GROUPID, USERID) VALUES ('ADMIN','admin@example.com');
