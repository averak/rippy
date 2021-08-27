package dev.abelab.rippy.db.entity;

import java.util.Date;

/**
 * Event Sample Builder
 */
public class EventSample extends AbstractSample {

    public static EventSampleBuilder builder() {
        return new EventSampleBuilder();
    }

    public static class EventSampleBuilder {

        private Integer id = SAMPLE_INT;
        private String name = SAMPLE_STR;
        private String description = SAMPLE_STR;
        private Integer ownerId = SAMPLE_INT;
        private Date expiredAt = SAMPLE_DATE;
        private Date createdAt = SAMPLE_DATE;
        private Date updatedAt = SAMPLE_DATE;

        public EventSampleBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public EventSampleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EventSampleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EventSampleBuilder ownerId(Integer ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public EventSampleBuilder expiredAt(Date expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        public EventSampleBuilder createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EventSampleBuilder updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Event build() {
            return Event.builder() //
                .id(this.id) //
                .name(this.name) //
                .description(this.description) //
                .ownerId(this.ownerId) //
                .expiredAt(this.expiredAt) //
                .createdAt(this.createdAt) //
                .updatedAt(this.updatedAt) //
                .build();
        }

    }

}
