package com.redhat.chartgeneration.graphcalc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.report.LineStop;

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

	public List<LineStop> produce(List<List<Object>> rows,
			FieldSelector xField, FieldSelector yField) {
		List<LineStop> stops = new LinkedList<LineStop>();
		Object lastX = 0;
		double y = 0.0;
		int count = 0;
		for (Iterator<List<Object>> it = rows.iterator(); it.hasNext();) {
			List<Object> row = it.next();
			Object x = xField.select(row);
			if (interval > 1) {
				if (Long.class.isAssignableFrom(x.getClass())) {
					Number num = (Number) x;
					x = num.longValue() / interval * interval;
				} else if (Double.class.isAssignableFrom(x.getClass())) {
					Number num = (Number) x;
					x = (long)(Math.floor(num.doubleValue() / interval) * interval);
				} else {
					// interval is omitted
					System.err.println("[Warning] AverageCalculation.produce: interval is omitted");
				}
			}

			if (lastX.equals(x)) {
				y += ((Number) yField.select(row)).doubleValue();
				++count;
			} else {
				if (count > 0)
					stops.add(new LineStop(lastX, y / count * times, count));
				y = ((Number) yField.select(row)).doubleValue();
				count = 1;
			}
			lastX = x;
		}
		if (count > 0)
			stops.add(new LineStop(lastX, y / count * times, count));
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
