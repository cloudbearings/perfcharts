package chartgeneration.configtemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chartgeneration.calc.AverageCalculation;
import chartgeneration.chart.Chart;
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
import chartgeneration.config.ChartConfig;
import chartgeneration.config.SeriesOrder;
import chartgeneration.perftest.calc.PerfCmpBarChartCalculation;
import chartgeneration.perftest.config.PerformanceComparisonTableConfig;

public class PerformanceComparisonChartTemplateOrderedByDifference extends
		Chart2DTemplateBase {

	@Override
	public Chart2DConfig generateChartConfig() {
		List<Chart2DSeriesConfigRule> rules;
		FieldSelector<Long> sourceSamplesField = new IndexFieldSelector(1);
		FieldSelector<Long> destSamplesField = new IndexFieldSelector(2);
		FieldSelector<Double> sourceRTField = new IndexFieldSelector<Double>(3);
		FieldSelector<Double> destRTField = new IndexFieldSelector<Double>(4);
		FieldSelector<String> sourceSeriesLabelField = new IndexFieldSelector<String>(13);
		FieldSelector<String> destSeriesLabelField = new IndexFieldSelector<String>(14);
		rules = new ArrayList<Chart2DSeriesConfigRule>();
		rules.add(new Chart2DSeriesConfigRule("TX-.+", destSeriesLabelField, "ms",
				getLabelField(), new StringExtractionTransformSelector(
						getLabelField(), "TX-(.+)", "$1"), destRTField,
				new PerfCmpBarChartCalculation(destSamplesField), false, true,
				false));
		rules.add(new Chart2DSeriesConfigRule("TX-.+", sourceSeriesLabelField, "ms",
				getLabelField(), new StringExtractionTransformSelector(
						getLabelField(), "TX-(.+)", "$1"), sourceRTField,
				new PerfCmpBarChartCalculation(sourceSamplesField), false,
				true, false));
		Chart2DConfig cfg = createConfig(
				"Transactions Average Response Time Comparison (Ordered by diff%)",
				"", "Response Time", rules, AxisMode.BAR_STRING);
		cfg.setSeriesOrder(SeriesOrder.NONE);
		cfg.setInterval(1); // disable auto interval
		cfg.setStringIDMapper(new BarChartStringIDMapper() {
			@Override
			public Map<String, Integer> map(Chart2D chart, Chart2DConfig config) {
				if (chart.getLines() == null || chart.getLines().size() < 2)
					return null;
				Map<String, Integer> result = new HashMap<String, Integer>();
				final Map<String, Double> thisMap = new HashMap<String, Double>();
				final Map<String, Double> diffMap = new HashMap<String, Double>();
				Chart2DSeries comparedSeries = chart.getLines().get(0);
				Chart2DSeries thisSeries = chart.getLines().get(1);
				for (Iterator<Point2D> it = thisSeries.getStops().iterator(); it
						.hasNext();) {
					Point2D point = it.next();
					if (point == null || Double.isNaN(point.getY())
							|| Double.isInfinite(point.getY())) {
						it.remove();
						continue;
					}
					thisMap.put(point.getX().toString(), point.getY());
				}
				for (Iterator<Point2D> it = comparedSeries.getStops()
						.iterator(); it.hasNext();) {
					Point2D point = it.next();
					Double thisRT = thisMap.get(point.getX().toString());
					if (thisRT == null || Double.isNaN(point.getY())
							|| Double.isInfinite(point.getY())) {
						it.remove();
						continue;
					}
					double v = (thisRT - point.getY()) / point.getY();
//					if (Double.isNaN(v) || Double.isInfinite(v))
//						System.err.println("xxxxx" + v);
					diffMap.put(point.getX().toString(), v);
				}
				Collections.sort(thisSeries.getStops(),
						new Comparator<Point2D>() {
							@Override
							public int compare(Point2D o1, Point2D o2) {
								Double a = diffMap.get(o1.getX().toString());
								Double b = diffMap.get(o2.getX().toString());
								if (a == b)
									return 0;
								if (a == null || Double.isNaN(a))
									return 1;
								if (b == null || Double.isNaN(b))
									return -1;
								if (a < b)
									return 1;
								if (a > b)
									return -1;
								return 0;
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