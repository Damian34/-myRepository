DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS NOTE;

CREATE TABLE USER (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  login VARCHAR(250) NOT NULL,
  password VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) DEFAULT NULL
);


CREATE TABLE NOTE (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  id_user INT NOT NULL,
  title VARCHAR(250) NOT NULL,
  message VARCHAR(2500) DEFAULT NULL
);

insert into USER (id,login,password,email,first_name,last_name) values 
(1,'user11','$2a$10$2YqtZoACkuR2S3D7qjeOqug3pxmutn1SLJ0tIyNy8auM7wJKXXEW.','user11@us','user11','user11');
