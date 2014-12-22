package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class NMONMemoryUtilizationChartTemplate extends
		BaseGraphTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector totalMemField = new IndexFieldSelector(2);
		FieldSelector freeMemField = new IndexFieldSelector(3);
		FieldSelector cachedMemField = new IndexFieldSelector(4);
		FieldSelector buffersMemField = new IndexFieldSelector(5);
		List<GraphSeriesConfigRule> rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^MEM$", "Total", "MiB", labelField,
				timestampField, totalMemField, new AverageCalculation(interval)));
		rules.add(new GraphSeriesConfigRule("^MEM$", "Free", "MiB", labelField,
				timestampField, freeMemField, new AverageCalculation(interval)));
		rules.add(new GraphSeriesConfigRule("^MEM$", "Cached", "MiB", labelField,
				timestampField, cachedMemField,
				new AverageCalculation(interval)));
		rules.add(new GraphSeriesConfigRule("^MEM$", "Buffers", "MiB", labelField,
				timestampField, buffersMemField, new AverageCalculation(
						interval)));
		return createConfig("Memory Utilization over Time", "time",
				"MiB", rules, AxisMode.TIME);
	}

}
