# Fruit Inventory Manage System
This temp project is for study task.

## Require
- Java 1.8
- MySQL 5.5

## Database config
- Database

`CREATE USER 'FMS'@'%' IDENTIFIED WITH mysql_native_password;`

`GRANT USAGE ON *.* TO 'FMS'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;`

`SET PASSWORD FOR 'FMS'@'%' = '123456';CREATE DATABASE IF NOT EXISTS ``FMS``;`

`GRANT ALL PRIVILEGES ON ``FMS``.* TO 'FMS'@'%';`

- Table

`TODO`