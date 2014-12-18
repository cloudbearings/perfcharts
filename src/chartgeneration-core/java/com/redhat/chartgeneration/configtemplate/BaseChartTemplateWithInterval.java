package com.redhat.chartgeneration.configtemplate;


public abstract class BaseChartTemplateWithInterval extends BaseGraphTemplate {
	private int interval = 0;

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
