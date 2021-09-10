CREATE TABLE IF NOT EXISTS `event_tagging` (
  `event_id` INT UNSIGNED NOT NULL,
  `tag_id` INT UNSIGNED NOT NULL,
  CONSTRAINT `fk_event_tagging_event_id`
    FOREIGN KEY (`event_id`)
    REFERENCES `event` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_tagging_tag_id`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tag` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
);
