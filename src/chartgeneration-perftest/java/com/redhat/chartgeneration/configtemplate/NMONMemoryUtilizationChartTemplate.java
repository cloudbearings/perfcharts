package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class NMONMemoryUtilizationChartTemplate extends
		BaseChartTemplateWithInterval {

	@Override
	public LineGraphConfig generateGraphConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector totalMemField = new IndexFieldSelector(2);
		FieldSelector freeMemField = new IndexFieldSelector(3);
		FieldSelector cachedMemField = new IndexFieldSelector(4);
		FieldSelector buffersMemField = new IndexFieldSelector(5);
		List<LineConfigRule> rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^MEM$", "Total", "MiB", labelField,
				timestampField, totalMemField, new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^MEM$", "Free", "MiB", labelField,
				timestampField, freeMemField, new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^MEM$", "Cached", "MiB", labelField,
				timestampField, cachedMemField,
				new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^MEM$", "Buffers", "MiB", labelField,
				timestampField, buffersMemField, new AverageCalculation(
						interval)));
		return createConfig("Memory Utilization over Time", "time",
				"MiB", rules, AxisMode.TIME);
	}

}
