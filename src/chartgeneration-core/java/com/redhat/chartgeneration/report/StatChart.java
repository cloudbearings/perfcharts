package com.redhat.chartgeneration.report;


public abstract class StatChart {
	private String title;
	private String subtitle;

	// private StatChartFormatter formatter;

	public StatChart() {

	}

	public StatChart(String title, String subtitle/*
												 * , StatChartFormatter
												 * formatter
												 */) {
		this.title = title;
		this.subtitle = subtitle;
		// this.setFormatter(formatter);
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
