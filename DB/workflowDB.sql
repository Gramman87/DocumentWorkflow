-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema userDB
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `userDB` ;

-- -----------------------------------------------------
-- Schema userDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `userDB` DEFAULT CHARACTER SET utf8 ;
USE `userDB` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(100) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `role` VARCHAR(75) NOT NULL,
  `enabled` TINYINT NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `workflow_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `workflow_type` ;

CREATE TABLE IF NOT EXISTS `workflow_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `workflow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `workflow` ;

CREATE TABLE IF NOT EXISTS `workflow` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `current_user` INT NOT NULL,
  `next_user` INT NOT NULL,
  `enabled` TINYINT NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `workflow_type_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_workflow_workflow_type1_idx` (`workflow_type_id` ASC),
  CONSTRAINT `fk_workflow_workflow_type1`
    FOREIGN KEY (`workflow_type_id`)
    REFERENCES `workflow_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `document_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `document_type` ;

CREATE TABLE IF NOT EXISTS `document_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(75) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `document`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `document` ;

CREATE TABLE IF NOT EXISTS `document` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `workflow_id` INT NOT NULL,
  `link` VARCHAR(2000) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` TINYINT NOT NULL,
  `document_type_id` INT NOT NULL,
  PRIMARY KEY (`id`, `workflow_id`),
  INDEX `fk_object_workflow1_idx` (`workflow_id` ASC),
  INDEX `fk_document_document_type1_idx` (`document_type_id` ASC),
  CONSTRAINT `fk_object_workflow1`
    FOREIGN KEY (`workflow_id`)
    REFERENCES `workflow` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_document_document_type1`
    FOREIGN KEY (`document_type_id`)
    REFERENCES `document_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_workflow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_workflow` ;

CREATE TABLE IF NOT EXISTS `user_workflow` (
  `user_id` INT NOT NULL,
  `workflow_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `workflow_id`),
  INDEX `fk_user_has_workflow_workflow1_idx` (`workflow_id` ASC),
  INDEX `fk_user_has_workflow_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_workflow_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_workflow_workflow1`
    FOREIGN KEY (`workflow_id`)
    REFERENCES `workflow` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS workflow@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'workflow'@'localhost' IDENTIFIED BY 'workflow';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'workflow'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
