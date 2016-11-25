package com.tricast.web.response;

public class PeriodTypeResponse {

		private long periodTypeId;
		private String description;
		
		public long getPeriodTypeId() {
			return periodTypeId;
		}
		
		public void setPeriodTypeId(long periodTypeId) {
			this.periodTypeId = periodTypeId;
		}
		
		public String getDescription() {
			return description;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
}
