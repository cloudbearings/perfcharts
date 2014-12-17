package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.generator.Generator;

public abstract class StatChartConfig implements ChartConfig {
	private String title;
	private String subtitle;

	public StatChartConfig() {

	}

	public StatChartConfig(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
	}
	
	public abstract Generator createGenerator() throws Exception;

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
