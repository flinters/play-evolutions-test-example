# create post table

# --- !Ups

CREATE TABLE `Posts`(
  `id`        VARCHAR(40)  NOT NULL,
  `author_id` VARCHAR(40)  NOT NULL,
  `title`     VARCHAR(128) NOT NULL,
  `content`   TEXT          NOT NULL,
  `posted`    DATETIME(6)   NOT NULL,
  `modified`  DATETIME(6)   NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`author_id`) REFERENCES `Users`(`id`)
) ENGINE = InnoDB, DEFAULT CHARSET = utf8mb4;

# --- !Downs

DROP TABLE IF EXISTS `Posts`;
