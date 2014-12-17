package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.config.GraphConfig;
import com.redhat.chartgeneration.graphcalc.AverageCalculation;
import com.redhat.chartgeneration.graphcalc.SumBySeriesCalculation;

public class NMONDiskBusyChartTemplate extends BaseChartTemplateWithInterval {

	@Override
	public GraphConfig generateChartConfig() {
		int interval = getInterval();
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector labelField = getLabelField();
		FieldSelector diskBusyField = new IndexFieldSelector(2);
		List<GraphLineConfigRule> rules = new ArrayList<GraphLineConfigRule>();
		rules.add(new GraphLineConfigRule("^DISKBUSY-(.+)$", "Disk-$1", "%", labelField,
				timestampField, diskBusyField, new AverageCalculation(interval)));
		rules.add(new GraphLineConfigRule("^DISKBUSY-(.+)$", "Total", "%", labelField,
				timestampField, diskBusyField, new SumBySeriesCalculation(labelField,
						interval)));
		return createConfig("Disk Busy% Over Time", "time", "%", rules, AxisMode.TIME);
	}

}
