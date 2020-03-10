CREATE TABLE student (
     id INT AUTO_INCREMENT PRIMARY KEY,
     first_name VARCHAR(30),
     last_name VARCHAR(30),
     program_name VARCHAR(30),
     program_year INTEGER,
     program_coop BOOLEAN,
     program_internship BOOLEAN
);

CREATE TABLE users (
   user_login VARCHAR(15) PRIMARY KEY,
   user_password VARCHAR(128) NOT NULL
);

CREATE TABLE roles (
   user_login VARCHAR(15) NOT NULL,
   user_role VARCHAR(15) NOT NULL,

   PRIMARY KEY (user_login, user_role),
   FOREIGN KEY(user_login)
       REFERENCES users(user_login)
);

CREATE TABLE persistent_logins (
   username varchar(100) not null,
   series varchar(64) primary key,
   token varchar(64) not null,
   last_used timestamp not null
);
