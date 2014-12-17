package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class CPULoadChartTemplate extends BaseChartTemplateWithInterval {
	@Override
	public GraphConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector cpuload1min = new IndexFieldSelector(2);
		FieldSelector cpuload5min = new IndexFieldSelector(3);
		FieldSelector cpuload15min = new IndexFieldSelector(4);
		FieldSelector coresField = new IndexFieldSelector(5);
		List<GraphLineConfigRule> rules = new ArrayList<GraphLineConfigRule>();
		rules.add(new GraphLineConfigRule("^CPULOAD$", "1 min", "LD", labelField,
				timestampField, cpuload1min, new AverageCalculation(interval)));
		rules.add(new GraphLineConfigRule("^CPULOAD$", "5 min", "LD", labelField,
				timestampField, cpuload5min, new AverageCalculation(interval)));
		rules.add(new GraphLineConfigRule("^CPULOAD$", "15 min", "LD", labelField,
				timestampField, cpuload15min, new AverageCalculation(interval)));
		rules.add(new GraphLineConfigRule("^CPULOAD$", "CPUs", "LD", labelField,
				timestampField, coresField, new AverageCalculation(interval)));
		return createConfig("CPU Load over Time", "time", "load", rules,
				AxisMode.TIME);
	}

}
