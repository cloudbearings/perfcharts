package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.ConstantSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;

public class CPULoadChartTemplate extends BaseChartTemplateWithInterval {
	@Override
	public LineGraphConfig generateGraphConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector cpuload1min = new IndexFieldSelector(2);
		FieldSelector cpuload5min = new IndexFieldSelector(3);
		FieldSelector cpuload15min = new IndexFieldSelector(4);
		List<LineConfigRule> rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^CPULOAD$", "1 min", "LD", labelField,
				timestampField, cpuload1min, new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^CPULOAD$", "5 min", "LD", labelField,
				timestampField, cpuload5min, new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^CPULOAD$", "15 min", "LD", labelField,
				timestampField, cpuload15min, new AverageCalculation(interval)));
		rules.add(new LineConfigRule("^CPULOAD$", "CPUs", "LD", labelField,
				timestampField, new ConstantSelector(2),
				new AverageCalculation(interval)));
		return createConfig("CPU Load over Time", "time", "mtx", rules,
				AxisMode.TIME);
	}

}
