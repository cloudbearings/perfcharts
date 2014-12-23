package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.calc.AverageCalculation;
import com.redhat.chartgeneration.calc.SumByLabelCalculation;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.Chart2DSeriesConfigRule;
import com.redhat.chartgeneration.config.Chart2DConfig;

public class NMONDiskBusyChartTemplate extends BaseChart2DTemplateWithInterval {

	@Override
	public Chart2DConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector diskBusyField = new IndexFieldSelector(2);
		List<Chart2DSeriesConfigRule> rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("^DISKBUSY-(.+)$", "Disk-$1", "%", labelField,
				timestampField, diskBusyField, new AverageCalculation(interval)));
		rules.add(new Chart2DSeriesConfigRule("^DISKBUSY-(.+)$", "Total", "%", labelField,
				timestampField, diskBusyField, new SumByLabelCalculation(labelField,
						interval)));
		return createConfig("Disk Busy% Over Time", "time", "%", rules, AxisMode.TIME);
	}

}
