package com.redhat.chartgeneration.configtemplate;


public abstract class BaseChartTemplateWithInterval extends BaseChartTemplate {
	private int interval = 1000;

	public BaseChartTemplateWithInterval() {

	}

	public BaseChartTemplateWithInterval(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
