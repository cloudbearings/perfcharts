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
import chartgeneration.config.Chart2DSeriesConfig;
import chartgeneration.config.Chart2DSeriesConfigRule;
import chartgeneration.config.Chart2DXValueComparator;
import chartgeneration.config.SeriesOrder;
import chartgeneration.perftest.calc.PerfCmpBarChartCalculation;

public class PerformanceComparisonChartTemplateOrderedByHits extends
		Chart2DTemplateBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector<Long> sourceSamplesField = new IndexFieldSelector(1);
		FieldSelector<Long> destSamplesField = new IndexFieldSelector(2);
		FieldSelector<Double> sourceRTField = new IndexFieldSelector<Double>(3);
		FieldSelector<Double> destRTField = new IndexFieldSelector<Double>(4);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("TX-.+", "compared build", "ms",
				getLabelField(), new StringExtractionTransformSelector(
						getLabelField(), "TX-(.+)", "$1"), destRTField,
				new PerfCmpBarChartCalculation(destSamplesField), false, true,
				false));
		rules.add(new Chart2DSeriesConfigRule("TX-.+", "this build", "ms",
				getLabelField(), new StringExtractionTransformSelector(
						getLabelField(), "TX-(.+)", "$1"), sourceRTField,
				new PerfCmpBarChartCalculation(sourceSamplesField), false,
				true, false));
		Chart2DConfig cfg = createConfig(
				"Transactions Average Response Time Comparison (Ordered by Hits)",
				"", "Response Time", rules, AxisMode.BAR_STRING);
		cfg.setSeriesOrder(SeriesOrder.NONE);
		cfg.setInterval(1); // disable auto interval
		cfg.setStringIDMapper(new BarChartStringIDMapper() {
			@Override
			public Map<String, Integer> map(Chart2D chart, Chart2DConfig config) {
				if (chart.getLines() == null || chart.getLines().size() < 2)
					return null;
				Map<String, Integer> result = new HashMap<String, Integer>();
				Chart2DSeries comparedSeries = chart.getLines().get(0);
				Chart2DSeries thisSeries = chart.getLines().get(1);
				Collections.sort(thisSeries.getStops(),
						new Comparator<Point2D>() {
							@Override
							public int compare(Point2D o1, Point2D o2) {
//								System.err.println(o2.getWeight() + " "
//										+ o1.getWeight() + " "
//										+ (o2.getWeight() - o1.getWeight()));
								return o2.getWeight() - o1.getWeight();
							}
						});

				for (int i = 0; i < thisSeries.getStops().size();) {
					String key = thisSeries.getStops().get(i).getX().toString();
					result.put(key, ++i);
				}
				return result;
			}
		});
		return cfg;
	}
}