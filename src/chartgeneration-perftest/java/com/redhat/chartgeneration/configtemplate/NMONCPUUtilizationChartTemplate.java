package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class NMONCPUUtilizationChartTemplate extends
		BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector userField = new IndexFieldSelector(2);
		FieldSelector systemField = new IndexFieldSelector(3);
		FieldSelector waitField = new IndexFieldSelector(4);
		FieldSelector totalField = new IndexFieldSelector(5);
		FieldSelector cpusField = new IndexFieldSelector(6);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^CPU$", "User%", "%", getLabelField(),
				timestampField, userField,
				new AverageCalculation(getInterval())));
		rules.add(new Chart2DSeriesConfigRule("^CPU$", "System%", "%", getLabelField(),
				timestampField, systemField, new AverageCalculation(
						getInterval())));
		rules.add(new Chart2DSeriesConfigRule("^CPU$", "Wait%", "%", getLabelField(),
				timestampField, waitField,
				new AverageCalculation(getInterval())));
		rules.add(new Chart2DSeriesConfigRule("^CPU$", "Used%", "%", getLabelField(),
				timestampField, totalField, new AverageCalculation(
						getInterval())));
		rules.add(new Chart2DSeriesConfigRule("^CPU$", "CPUs", "", getLabelField(),
				timestampField, cpusField,
				new AverageCalculation(getInterval())));
		return createConfig("CPU Utilization over Time", "time", "%", rules,
				AxisMode.TIME);
	}

}
