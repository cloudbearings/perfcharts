package com.redhat.chartgeneration.report;

import java.util.List;

public class Report {
	private String title;
	private List<Chart> charts;

	public Report() {

	}

	public Report(String title, List<Chart> charts) {
		this.title = title;
		this.charts = charts;
	}

	public List<Chart> getCharts() {
		return charts;
	}

	public void setGraphs(List<Chart> charts) {
		this.charts = charts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
