package com.redhat.chartgeneration.configtemplate;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.ChartConfig;

public interface ChartConfigTemplate {
	public FieldSelector getLabelField();

	public void setLabelField(FieldSelector labelField);

	public ChartConfig generateChartConfig();
}
