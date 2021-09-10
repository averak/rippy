package dev.abelab.rippy.db.entity;

/**
 * EventTagging Sample Builder
 */
public class EventTaggingSample extends AbstractSample {

	public static EventTaggingSampleBuilder builder() {
		return new EventTaggingSampleBuilder();
	}

	public static class EventTaggingSampleBuilder {

		private Integer eventId = SAMPLE_INT;
		private Integer tagId = SAMPLE_INT;

		public EventTaggingSampleBuilder eventId(Integer eventId) {
			this.eventId = eventId;
			return this;
		}

		public EventTaggingSampleBuilder tagId(Integer tagId) {
			this.tagId = tagId;
			return this;
		}

		public EventTagging build() {
			return EventTagging.builder() //
				.eventId(this.eventId) //
				.tagId(this.tagId) //
				.build();
		}

	}

}
