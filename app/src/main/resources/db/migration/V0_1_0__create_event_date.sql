CREATE TABLE IF NOT EXISTS `event_date` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `event_id` INT UNSIGNED NOT NULL,
  `date_order` INT UNSIGNED NOT NULL,
  `start_at` DATETIME NOT NULL,
  `finish_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`event_id`, `date_order`),
  CONSTRAINT `fk_event_date_event_id`
    FOREIGN KEY (`event_id`)
    REFERENCES `event` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);
