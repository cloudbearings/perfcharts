package chartgeneration.calc;

import java.util.LinkedList;
import java.util.List;

import chartgeneration.chart.Point2D;
import chartgeneration.common.FieldSelector;

/**
 * This calculation groups data rows by their x-fields, calculates the average
 * among their y-fields of each group, and makes the average values as result
 * y-values.
 * 
 * @author Rayson Zhu
 *
 */
public class AverageCalculation implements Chart2DCalculation {
	/**
	 * the interval for point merging
	 */
	private int interval = 0;

	public AverageCalculation() {

	}

	public AverageCalculation(int interval) {
		this.interval = interval;
	}

	public List<Point2D> produce(List<List<Object>> rows, FieldSelector xField,
			FieldSelector yField) {
		List<Point2D> stops = new LinkedList<Point2D>();
		if (rows.isEmpty())
			return stops;
		Number firstX = (Number) xField.select(rows.get(0));
		Number lastX = 0;
		double y = 0.0;
		int count = 0;
		for (List<Object> row : rows) {
			Number x = (Number) xField.select(row);
			if (interval > 1) {
				x = firstX.longValue() + (x.longValue() - firstX.longValue())
						/ interval * interval;
			}

			if (lastX.equals(x)) {
				y += ((Number) yField.select(row)).doubleValue();
				++count;
			} else {
				if (count > 0)
					stops.add(new Point2D(lastX, y / count, count));
				y = ((Number) yField.select(row)).doubleValue();
				count = 1;
			}
			lastX = x;
		}
		if (count > 0)
			stops.add(new Point2D(lastX, y / count, count));
		return stops;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
