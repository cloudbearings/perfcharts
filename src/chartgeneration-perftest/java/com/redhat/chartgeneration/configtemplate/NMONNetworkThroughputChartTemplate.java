package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class NMONNetworkThroughputChartTemplate extends
		BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector labelField = getLabelField();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector netReadField = new IndexFieldSelector(2);
		FieldSelector netWriteField = new IndexFieldSelector(3);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^NET$", "Read", "KiB/s", labelField,
				timestampField, netReadField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^NET$", "Write", "KiB/s", labelField,
				timestampField, netWriteField, new AverageCalculation(interval)));
		return createConfig("Network Throughput over Time", "time", "KiB/s",
				rules, AxisMode.TIME);
	}

}
