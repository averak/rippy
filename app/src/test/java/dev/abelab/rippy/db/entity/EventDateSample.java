package dev.abelab.rippy.db.entity;

import java.util.Date;

/**
 * EventDate Sample Builder
 */
public class EventDateSample extends AbstractSample {

	public static EventDateSampleBuilder builder() {
		return new EventDateSampleBuilder();
	}

	public static class EventDateSampleBuilder {

		private Integer id = SAMPLE_INT;
		private Integer eventId = SAMPLE_INT;
		private Integer dateOrder = SAMPLE_INT;
		private Date startAt = SAMPLE_DATE;
		private Date finishAt = SAMPLE_DATE;

		public EventDateSampleBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public EventDateSampleBuilder eventId(Integer eventId) {
			this.eventId = eventId;
			return this;
		}

		public EventDateSampleBuilder dateOrder(Integer dateOrder) {
			this.dateOrder = dateOrder;
			return this;
		}

		public EventDateSampleBuilder startAt(Date startAt) {
			this.startAt = startAt;
			return this;
		}

		public EventDateSampleBuilder finishAt(Date finishAt) {
			this.finishAt = finishAt;
			return this;
		}

		public EventDate build() {
			return EventDate.builder() //
				.id(this.id) //
				.eventId(this.eventId) //
				.dateOrder(this.dateOrder) //
				.startAt(this.startAt) //
				.finishAt(this.finishAt) //
				.build();
		}

	}

}
