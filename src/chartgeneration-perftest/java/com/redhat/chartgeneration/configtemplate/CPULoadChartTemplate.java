package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class CPULoadChartTemplate extends BaseChart2DTemplateWithInterval {
	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector cpuload1min = new IndexFieldSelector(2);
		FieldSelector cpuload5min = new IndexFieldSelector(3);
		FieldSelector cpuload15min = new IndexFieldSelector(4);
		FieldSelector coresField = new IndexFieldSelector(5);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "1 min", "LD", labelField,
				timestampField, cpuload1min, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "5 min", "LD", labelField,
				timestampField, cpuload5min, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "15 min", "LD", labelField,
				timestampField, cpuload15min, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^CPULOAD$", "CPUs", "LD", labelField,
				timestampField, coresField, new AverageCalculation(interval)));
		return createConfig("CPU Load over Time", "time", "load", rules,
				AxisMode.TIME);
	}

}
