package com.redhat.chartgeneration.configtemplate;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.report.Chart;

public interface ChartConfigTemplate {
	public FieldSelector getLabelField();

	public void setLabelField(FieldSelector labelField);

	public ChartConfig<? extends Chart> generateChartConfig();
}
