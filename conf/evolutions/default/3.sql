# add `age` column to `Users` table

# --- !Ups

ALTER TABLE `Users` ADD COLUMN `age` INT AFTER `email`;

# --- !Downs

ALTER TABLE `Users` DROP COLUMN `age`;
