package com.redhat.chartgeneration.report;


public abstract class Chart {
	private String title;
	private String subtitle;

	public Chart() {

	}

	public Chart(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
	}

	public abstract String format() throws Exception;

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
