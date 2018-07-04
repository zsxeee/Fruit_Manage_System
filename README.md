# Fruit Inventory Manage System
This temp project is for study task.

## Require
- Java 1.8
- MySQL 5.5

## Dependencies
- Mysql Connector
- JIconfont Swing (font awesome)
- BeautyEye

## Database config
- Database

```sql
CREATE USER 'fms'@'%' IDENTIFIED WITH mysql_native_password;
```

```sql
ALTER DATABASE `fms` DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
```

```sql
GRANT USAGE ON *.* TO 'fms'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
```

```sql
SET PASSWORD FOR 'fms'@'%' = '123456';CREATE DATABASE IF NOT EXISTS `fms`;
```

```sql
GRANT ALL PRIVILEGES ON `fms`.* TO 'fms'@'%';
```

- Table

```sql
CREATE TABLE `fms`.`FruitInfo` ( `FruitNumber` INT(3) NOT NULL AUTO_INCREMENT COMMENT '水果的编号' , `FruitName` VARCHAR(10) NOT NULL COMMENT '水果的名称' , `FruitProduction` VARCHAR(10) NOT NULL COMMENT '水果的产地' , PRIMARY KEY (`FruitNumber`)) ENGINE = MyISAM;
```

```sql
CREATE TABLE `fms`.`FruitInventory` ( `BatchNumber` INT(5) NOT NULL AUTO_INCREMENT COMMENT '批次编号' , `FruitNumber` INT(3) NOT NULL COMMENT '水果的编号' , `BatchDinout` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '水果库存变动时间' , `BatchType` BIT(1) NOT NULL COMMENT '批次变动类型' , `BatchQuantity` FLOAT(4,2) NOT NULL COMMENT '本批次水果的数量(kg)' , `BatchPrice` FLOAT(2,2) NOT NULL COMMENT '当前批次单价' , `BatchSupplier` VARCHAR(10) NOT NULL COMMENT '本批次水果供应商' , PRIMARY KEY (`BatchNumber`),FOREIGN KEY (`FruitNumber`) REFERENCES `fms`.`FruitInfo`(`FruitNumber`)) ENGINE = MyISAM;
```