package com.redhat.chartgeneration.graphcalc;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.redhat.chartgeneration.common.AppData;
import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.report.GraphPoint;

public class AverageCalculation implements GraphCalculation {
	private int interval = 0;
	private int times = 1;

	public AverageCalculation() {

	}

	public AverageCalculation(int interval) {
		this.interval = interval;
	}

	public AverageCalculation(int interval, int times) {
		this.interval = interval;
		this.times = times;
	}

	public List<GraphPoint> produce(List<List<Object>> rows,
			FieldSelector xField, FieldSelector yField) {
		List<GraphPoint> stops = new LinkedList<GraphPoint>();
		Object lastX = 0;
		double y = 0.0;
		int count = 0;
		for (List<Object> row : rows) {
			Object x = xField.select(row);
			if (interval > 1) {
				if (Long.class.isAssignableFrom(x.getClass())) {
					Number num = (Number) x;
					x = num.longValue() / interval * interval;
				} else if (Double.class.isAssignableFrom(x.getClass())) {
					Number num = (Number) x;
					x = (long) (Math.floor(num.doubleValue() / interval) * interval);
				} else {
					Logger logger = AppData.getInstance().getLogger();
					logger.warning("'interval' is omitted because '"
							+ x.toString() + "' (whose type is '"
							+ x.getClass().toString() + "') is not a number.");
				}
			}

			if (lastX.equals(x)) {
				y += ((Number) yField.select(row)).doubleValue();
				++count;
			} else {
				if (count > 0)
					stops.add(new GraphPoint(lastX, y / count * times, count));
				y = ((Number) yField.select(row)).doubleValue();
				count = 1;
			}
			lastX = x;
		}
		if (count > 0)
			stops.add(new GraphPoint(lastX, y / count * times, count));
		return stops;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

}
