package dev.abelab.rippy.db.entity;

/**
 * EventAnswerDate Sample Builder
 */
public class EventAnswerDateSample extends AbstractSample {

	public static EventAnswerDateSampleBuilder builder() {
		return new EventAnswerDateSampleBuilder();
	}

	public static class EventAnswerDateSampleBuilder {

		private Integer answerId = SAMPLE_INT;
		private Integer dateId = SAMPLE_INT;
		private Boolean isPossible = SAMPLE_BOOL;

		public EventAnswerDateSampleBuilder answerId(Integer answerId) {
			this.answerId = answerId;
			return this;
		}

		public EventAnswerDateSampleBuilder dateId(Integer dateId) {
			this.dateId = dateId;
			return this;
		}

		public EventAnswerDateSampleBuilder isPossible(Boolean isPossible) {
			this.isPossible = isPossible;
			return this;
		}

		public EventAnswerDate build() {
			return EventAnswerDate.builder() //
				.answerId(this.answerId) //
				.dateId(this.dateId) //
				.isPossible(this.isPossible) //
				.build();
		}

	}

}
