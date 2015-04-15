package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.List;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.common.StringExtractionTransformSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.config.SeriesOrder;

public class PerformanceComparisonChartTemplate extends Chart2DTemplateBase{

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector<Double> sourceRTField = new IndexFieldSelector<Double> (3);
		FieldSelector<Double> destRTField = new IndexFieldSelector<Double> (4);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("TX-.+", "compared build", "ms",
				getLabelField(), new StringExtractionTransformSelector(getLabelField(), "TX-(.+)", "$1"), destRTField,
				new AverageCalculation(), false, true, false));
		rules.add(new Chart2DSeriesConfigRule("TX-.+", "this build", "ms",
				getLabelField(), new StringExtractionTransformSelector(getLabelField(), "TX-(.+)", "$1"), sourceRTField,
				new AverageCalculation(), false, true, false));
		Chart2DConfig cfg = createConfig("Transactions Average Response Time Comparison", "", "Response Time",
				rules, AxisMode.BAR_STRING);
		cfg.setSeriesOrder(SeriesOrder.NONE);
		cfg.setInterval(1); //disable auto interval
		return cfg;
	}

}