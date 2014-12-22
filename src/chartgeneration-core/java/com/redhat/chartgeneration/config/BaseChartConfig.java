package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.report.Chart;

public abstract class BaseChartConfig<T extends Chart> implements ChartConfig<T> {
	private String title;
	private String subtitle;

	public BaseChartConfig() {

	}

	public BaseChartConfig(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
	}
	
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
