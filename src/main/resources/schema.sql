DROP DATABASE IF EXISTS ra_db;

CREATE DATABASE ra_db
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE ra_db;

CREATE TABLE roles
(
  id   INT          NOT NULL,
  role VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id                INT          NOT NULL AUTO_INCREMENT,
  role_id           INT,
  email             VARCHAR(255) NOT NULL UNIQUE,
  login             VARCHAR(255) NOT NULL UNIQUE,
  password          VARCHAR(255) NOT NULL,
  firstName         VARCHAR(255) NOT NULL,
  lastName          VARCHAR(255) NOT NULL,
  avatar            VARCHAR(255)          DEFAULT NULL,
  banExpirationDate DATETIME              DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (role_id) REFERENCES roles (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE risk_groups (
  id        INT          NOT NULL AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL UNIQUE,
  mandatory BOOLEAN               DEFAULT FALSE,
  PRIMARY KEY (id)
);

CREATE TABLE risks (
  id            INT          NOT NULL AUTO_INCREMENT,
  risk_group_id INT,
  description   VARCHAR(255) NOT NULL,
  creator_id    INT          NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (creator_id) REFERENCES users (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE filled_risks (
  id          INT NOT NULL AUTO_INCREMENT,
  risk_id     INT,
  probability INT,
  damage      INT,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (risk_id) REFERENCES risks (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

-- DELIMITER //
-- CREATE TRIGGER field_risk_validator
-- BEFORE INSERT
--   ON risks
-- FOR EACH ROW
--   BEGIN
--     IF (NEW.probability < 0 OR NEW.probability > 5)
--     THEN CALL `Error: The probability value must be in the range [1; 5]`;
--     END IF;
--     IF (NEW.damage < 0 OR NEW.damage > 5)
--     THEN CALL `Error: The damage value must be in the range [1; 5]`;
--     END IF;
--   END;
-- //
-- DELIMITER ;