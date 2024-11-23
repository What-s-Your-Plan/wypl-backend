package com.wypl.googleoauthclient.data.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class BirthdayResponse {
	private String resourceName;
	private String etag;
	private List<Birthday> birthdays;

	public boolean emptyBirthday() {
		return birthdays == null;
	}

	@Getter
	@Builder
	public static class Birthday {
		private Metadata metadata;
		private Date date;

		@Getter
		@Builder
		public static class Metadata {
			private boolean primary;
			private Source source;

			@Getter
			@Builder
			public static class Source {
				private String type;
				private String id;
			}
		}

		@Getter
		@Setter
		public static class Date {
			private int year;
			private int month;
			private int day;
		}
	}
}

