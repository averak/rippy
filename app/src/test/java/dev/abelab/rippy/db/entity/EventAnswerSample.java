package dev.abelab.rippy.db.entity;

import java.util.Date;

/**
 * EventAnswer Sample Builder
 */
public class EventAnswerSample extends AbstractSample {

	public static EventAnswerSampleBuilder builder() {
		return new EventAnswerSampleBuilder();
	}

	public static class EventAnswerSampleBuilder {

		private Integer id = SAMPLE_INT;
		private Integer userId = SAMPLE_INT;
		private Integer eventId = SAMPLE_INT;
		private String comment = SAMPLE_STR;
		private Date createdAt = SAMPLE_DATE;
		private Date updatedAt = SAMPLE_DATE;

		public EventAnswerSampleBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public EventAnswerSampleBuilder userId(Integer userId) {
			this.userId = userId;
			return this;
		}

		public EventAnswerSampleBuilder eventId(Integer eventId) {
			this.eventId = eventId;
			return this;
		}

		public EventAnswerSampleBuilder comment(String comment) {
			this.comment = comment;
			return this;
		}

		public EventAnswerSampleBuilder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public EventAnswerSampleBuilder updatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public EventAnswer build() {
			return EventAnswer.builder() //
				.id(this.id) //
				.userId(this.userId) //
				.eventId(this.eventId) //
				.comment(this.comment) //
				.createdAt(this.createdAt) //
				.updatedAt(this.updatedAt) //
				.build();
		}

	}

}
