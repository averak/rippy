CREATE TABLE IF NOT EXISTS `event_answer_date` (
  `answer_id` INT UNSIGNED NOT NULL,
  `date_id` INT UNSIGNED NOT NULL,
  `is_possible` TINYINT(1) NOT NULL,
  CONSTRAINT `fk_event_answer_date_answer_id`
    FOREIGN KEY (`answer_id`)
    REFERENCES `event_answer` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_answer_date_date_id`
    FOREIGN KEY (`date_id`)
    REFERENCES `event_date` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);
