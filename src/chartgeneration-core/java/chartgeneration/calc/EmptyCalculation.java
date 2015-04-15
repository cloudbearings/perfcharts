package chartgeneration.calc;

import java.util.LinkedList;
import java.util.List;

import chartgeneration.chart.Point2D;
import chartgeneration.common.FieldSelector;

public class EmptyCalculation implements Chart2DCalculation {

	@Override
	public List<Point2D> produce(List<List<Object>> rows, FieldSelector xField,
			FieldSelector yField) {
		List<Point2D> stops = new LinkedList<Point2D>();
		if (rows.isEmpty())
			return stops;
		Comparable<?> firstX = (Comparable<?>)xField.select(rows.get(0));
		Comparable<?> lastX = firstX;
		double y = 0.0;
		int count = 0;
		for (List<Object> row : rows) {
			Comparable<?> x = (Comparable<?>)xField.select(row);
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

	@Override
	public int getInterval() {
		return 0;
	}

	@Override
	public void setInterval(int interval) {
	}

}
