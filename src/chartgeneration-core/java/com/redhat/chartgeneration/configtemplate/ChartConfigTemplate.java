package com.redhat.chartgeneration.configtemplate;

import com.redhat.chartgeneration.chart.Chart;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.ChartConfig;

/**
 * A {@link ChartConfigTemplate} is an factory object to create {@link ChartConfig}.
 * It can be used to define a template of specific chart.
 * 
 * @author Rayson Zhu
 *
 */
public interface ChartConfigTemplate {
	/**
	 * get the label field of data row
	 * 
	 * @return the label field
	 */
	public FieldSelector getLabelField();

	/**
	 * set the label field of data row
	 * 
	 * @param labelField
	 *            the label field
	 */
	public void setLabelField(FieldSelector labelField);

	/**
	 * Create a {@link ChartConfig} object to generate this kind of chart.
	 * 
	 * @return a {@link ChartConfig}
	 */
	public ChartConfig<? extends Chart> generateChartConfig();
}
