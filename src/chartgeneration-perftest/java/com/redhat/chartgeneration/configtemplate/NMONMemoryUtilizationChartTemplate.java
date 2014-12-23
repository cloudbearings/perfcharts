package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class NMONMemoryUtilizationChartTemplate extends
		BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector totalMemField = new IndexFieldSelector(2);
		FieldSelector freeMemField = new IndexFieldSelector(3);
		FieldSelector cachedMemField = new IndexFieldSelector(4);
		FieldSelector buffersMemField = new IndexFieldSelector(5);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Total", "MiB", labelField,
				timestampField, totalMemField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Free", "MiB", labelField,
				timestampField, freeMemField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Cached", "MiB", labelField,
				timestampField, cachedMemField,
				new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^MEM$", "Buffers", "MiB", labelField,
				timestampField, buffersMemField, new AverageCalculation(
						interval)));
		return createConfig("Memory Utilization over Time", "time",
				"MiB", rules, AxisMode.TIME);
	}

}
