package dev.abelab.rippy.db.entity;

import java.util.Date;

/**
 * Tag Sample Builder
 */
public class TagSample extends AbstractSample {

	public static TagSampleBuilder builder() {
		return new TagSampleBuilder();
	}

	public static class TagSampleBuilder {

		private Integer id = SAMPLE_INT;
		private String name = SAMPLE_STR;
		private Date createdAt = SAMPLE_DATE;
		private Date updatedAt = SAMPLE_DATE;

		public TagSampleBuilder id(Integer id) {
			this.id = id;
			return this;
		}

		public TagSampleBuilder name(String name) {
			this.name = name;
			return this;
		}

		public TagSampleBuilder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public TagSampleBuilder updatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public Tag build() {
			return Tag.builder() //
				.id(this.id) //
				.name(this.name) //
				.createdAt(this.createdAt) //
				.updatedAt(this.updatedAt) //
				.build();
		}

	}

}
