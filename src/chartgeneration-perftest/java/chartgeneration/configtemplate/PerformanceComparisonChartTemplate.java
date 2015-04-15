package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.chart.Chart2D;
import chartgeneration.chart.Chart2DSeries;
import chartgeneration.chart.Point2D;
import chartgeneration.common.FieldSelector;
import chartgeneration.common.IndexFieldSelector;
import chartgeneration.common.StringExtractionTransformSelector;
import chartgeneration.config.AxisMode;
import chartgeneration.config.BarChartStringIDMapper;
import chartgeneration.config.Chart2DConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.config.SeriesOrder;

public class PerformanceComparisonChartTemplate extends Chart2DTemplateBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector<Double> sourceRTField = new IndexFieldSelector<Double>(3);
		FieldSelector<Double> destRTField = new IndexFieldSelector<Double>(4);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("TX-.+", "compared build", "ms",
				getLabelField(), new StringExtractionTransformSelector(
						getLabelField(), "TX-(.+)", "$1"), destRTField,
				new AverageCalculation(), false, true, false));
		rules.add(new Chart2DSeriesConfigRule("TX-.+", "this build", "ms",
				getLabelField(), new StringExtractionTransformSelector(
						getLabelField(), "TX-(.+)", "$1"), sourceRTField,
				new AverageCalculation(), false, true, false));
		Chart2DConfig cfg = createConfig(
				"Transactions Average Response Time Comparison", "",
				"Response Time", rules, AxisMode.BAR_STRING);
		cfg.setSeriesOrder(SeriesOrder.NONE);
		cfg.setInterval(1); // disable auto interval
		cfg.setStringIDMapper(new BarChartStringIDMapper() {
			@Override
			public Map<String, Integer> map(Chart2D chart, Chart2DConfig config) {
				if (chart.getLines() == null || chart.getLines().isEmpty())
					return null;
				Map<String, Integer> result = new HashMap<String, Integer>();
				int count = 0;
				for (Chart2DSeries series : chart.getLines()) {
					for (Point2D point : series.getStops()) {
						Integer stringID = result.get(point.getX().toString());
						if (stringID == null) {
							stringID = ++count;
							result.put(point.getX().toString(), stringID);
						}
					}
				}
				return result;
			}
		});
		return cfg;
	}
}