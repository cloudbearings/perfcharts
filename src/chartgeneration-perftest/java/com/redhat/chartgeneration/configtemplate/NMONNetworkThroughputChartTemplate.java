package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class NMONNetworkThroughputChartTemplate extends
		BaseChartTemplateWithInterval {

	@Override
	public LineGraphConfig generateGraphConfig() {
		int interval = getInterval();
		FieldSelector labelField = getLabelField();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector netReadField = new IndexFieldSelector(2);
		FieldSelector netWriteField = new IndexFieldSelector(3);
		List<LineConfigRule> rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^NET$", "Read", "KiB/s", labelField,
				timestampField, netReadField, new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^NET$", "Write", "KiB/s", labelField,
				timestampField, netWriteField, new AverageCalculation(interval)));
		return createConfig("Network Throughput over Time", "time", "KiB/s",
				rules, AxisMode.TIME);
	}

}
