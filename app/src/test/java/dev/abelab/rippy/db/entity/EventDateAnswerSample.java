package dev.abelab.rippy.db.entity;

/**
 * EventDateAnswer Sample Builder
 */
public class EventDateAnswerSample extends AbstractSample {

	public static EventDateAnswerSampleBuilder builder() {
		return new EventDateAnswerSampleBuilder();
	}

	public static class EventDateAnswerSampleBuilder {

		private Integer answerId = SAMPLE_INT;
		private Integer dateId = SAMPLE_INT;
		private Boolean isPossible = SAMPLE_BOOL;

		public EventDateAnswerSampleBuilder answerId(Integer answerId) {
			this.answerId = answerId;
			return this;
		}

		public EventDateAnswerSampleBuilder dateId(Integer dateId) {
			this.dateId = dateId;
			return this;
		}

		public EventDateAnswerSampleBuilder isPossible(Boolean isPossible) {
			this.isPossible = isPossible;
			return this;
		}

		public EventDateAnswer build() {
			return EventDateAnswer.builder() //
				.answerId(this.answerId) //
				.dateId(this.dateId) //
				.isPossible(this.isPossible) //
				.build();
		}

	}

}
