package com.redhat.chartgeneration.configtemplate;


public abstract class BaseGraphTemplateWithInterval extends BaseGraphTemplate {
	private int interval = 0;

	public BaseGraphTemplateWithInterval() {

	}

	public BaseGraphTemplateWithInterval(int interval) {
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
