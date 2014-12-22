package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class NMONCPUUtilizationChartTemplate extends
		BaseGraphTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector userField = new IndexFieldSelector(2);
		FieldSelector systemField = new IndexFieldSelector(3);
		FieldSelector waitField = new IndexFieldSelector(4);
		FieldSelector totalField = new IndexFieldSelector(5);
		FieldSelector cpusField = new IndexFieldSelector(6);
		List<GraphSeriesConfigRule> rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^CPU$", "User%", "%", getLabelField(),
				timestampField, userField,
				new AverageCalculation(getInterval())));
		rules.add(new GraphSeriesConfigRule("^CPU$", "System%", "%", getLabelField(),
				timestampField, systemField, new AverageCalculation(
						getInterval())));
		rules.add(new GraphSeriesConfigRule("^CPU$", "Wait%", "%", getLabelField(),
				timestampField, waitField,
				new AverageCalculation(getInterval())));
		rules.add(new GraphSeriesConfigRule("^CPU$", "Used%", "%", getLabelField(),
				timestampField, totalField, new AverageCalculation(
						getInterval())));
		rules.add(new GraphSeriesConfigRule("^CPU$", "CPUs", "", getLabelField(),
				timestampField, cpusField,
				new AverageCalculation(getInterval())));
		return createConfig("CPU Utilization over Time", "time", "%", rules,
				AxisMode.TIME);
	}

}
