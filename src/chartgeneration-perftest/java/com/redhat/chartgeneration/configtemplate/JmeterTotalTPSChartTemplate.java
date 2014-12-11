package com.redhat.chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import com.redhat.chartgeneration.common.AddTransformSelector;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.common.IndexFieldSelector;
import com.redhat.chartgeneration.config.AxisMode;
import com.redhat.chartgeneration.config.LineConfigRule;
import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.graphcalc.CountCalculation;

public class JmeterTotalTPSChartTemplate extends
		BaseChartTemplateWithInterval {

	@Override
	public LineGraphConfig generateGraphConfig() {
		List<LineConfigRule> rules;
		FieldSelector timestampField = new IndexFieldSelector(1);
		FieldSelector rtField = new IndexFieldSelector(5);
		FieldSelector xField = new AddTransformSelector(timestampField, rtField);
		rules = new ArrayList<LineConfigRule>();
		rules.add(new LineConfigRule("^TX-(.+)-S$", "Success", "TPS",
				getLabelField(), xField, null, new CountCalculation(
						getInterval(), 1000.0 / getInterval(), false)));
		rules.add(new LineConfigRule("^TX-(.+)-F$", "Failure", "TPS",
				getLabelField(), xField, null, new CountCalculation(
						getInterval(), 1000.0 / getInterval(), true)));
		return createConfig("Total TPS over Time", "time", "TPS", rules,
				AxisMode.TIME);
	}

}
