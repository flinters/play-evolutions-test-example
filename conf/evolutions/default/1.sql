# create user table

# --- !Ups

CREATE TABLE `Users`(
  `id`       VARCHAR(40)  NOT NULL,
  `name`     VARCHAR(255) NOT NULL,
  `email`    VARCHAR(255) NOT NULL,
  `created`  DATETIME(6)   NOT NULL,
  `modified` DATETIME(6)   NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE KEY(`email`)
) ENGINE = InnoDB, DEFAULT CHARSET = utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS `Users`;
