package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.chart.Chart;

/**
 * BaseChartConfig is the base class for chart configuration.
 * 
 * @author Rayson Zhu
 *
 * @param <T>
 *            the type of chart
 */
public abstract class BaseChartConfig<T extends Chart> implements
		ChartConfig<T> {
	/**
	 * the title of configured chart
	 */
	private String title;
	/**
	 * the subtitle of configured chart
	 */
	private String subtitle;

	public BaseChartConfig() {

	}

	public BaseChartConfig(String title, String subtitle) {
		this.title = title;
		this.subtitle = subtitle;
	}

	/**
	 * get the subtitle of configured chart
	 * @return a subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * set the subtitle of configured hart
	 * @param subtitle a subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * get the title of configured chart
	 * @return a title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set the title of configured chart
	 * 
	 * @param title
	 *            a title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
