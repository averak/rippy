CREATE TABLE IF NOT EXISTS `event_answer_date` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `event_id` INT UNSIGNED NOT NULL,
  `possible_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_event_date_event_id`
    FOREIGN KEY (`event_id`)
    REFERENCES `event` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);
