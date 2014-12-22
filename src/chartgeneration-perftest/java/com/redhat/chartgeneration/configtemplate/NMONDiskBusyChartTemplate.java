package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphSeriesConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;
import com.redhat.chartgeneration.graphcalc.SumBySeriesCalculation;

public class NMONDiskBusyChartTemplate extends BaseGraphTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector diskBusyField = new IndexFieldSelector(2);
		List<GraphSeriesConfigRule> rules = new ArrayList<GraphSeriesConfigRule>();
		rules.add(new GraphSeriesConfigRule("^DISKBUSY-(.+)$", "Disk-$1", "%", labelField,
				timestampField, diskBusyField, new AverageCalculation(interval)));
		rules.add(new GraphSeriesConfigRule("^DISKBUSY-(.+)$", "Total", "%", labelField,
				timestampField, diskBusyField, new SumBySeriesCalculation(labelField,
						interval)));
		return createConfig("Disk Busy% Over Time", "time", "%", rules, AxisMode.TIME);
	}

}
