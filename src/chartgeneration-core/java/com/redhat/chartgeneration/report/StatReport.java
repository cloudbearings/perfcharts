package com.redhat.chartgeneration.report;

import java.util.List;

public class StatReport {
	private String title;
	private List<StatChart> charts;

	public StatReport() {

	}

	public StatReport(String title, List<StatChart> charts) {
		this.title = title;
		this.charts = charts;
	}

	public List<StatChart> getCharts() {
		return charts;
	}

	public void setGraphs(List<StatChart> charts) {
		this.charts = charts;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
