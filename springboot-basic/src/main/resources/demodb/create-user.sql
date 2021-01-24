DROP USER IF EXISTS 'newuser'@'localhost';
CREATE USER 'newuser'@'localhost' IDENTIFIED BY 'newuser123';
GRANT ALL PRIVILEGES ON demodb.* TO 'newuser'@'localhost';
FLUSH PRIVILEGES ;

DROP DATABASE IF EXISTS demodb;
CREATE DATABASE demodb;