package com.redhat.chartgeneration.graphcalc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.report.LineStop;

public class SumCalculation implements GraphCalculation {
	private int interval = 1000;
	private int times = 1;

	public SumCalculation() {

	}

	public SumCalculation(int interval) {
		this.interval = interval;
	}

	public SumCalculation(int interval, int times) {
		this.interval = interval;
		this.times = times;
	}

	public List<LineStop> produce(List<List<Object>> rows,
			FieldSelector xField, FieldSelector yField) {
		List<LineStop> stops = new LinkedList<LineStop>();
		Object lastX = 0;
		double y = 0.0;
		int count = 0;
		//double lastY = 0.0;
		for (Iterator<List<Object>> it = rows.iterator(); it.hasNext();) {
			List<Object> row = it.next();
			Object x = xField.select(row);
			if (interval > 1) {
				if (Long.class.isAssignableFrom(x.getClass())) {
					Number num = (Number) x;
					x = num.longValue() / interval * interval;
				} else if (Double.class.isAssignableFrom(x.getClass())) {
					Number num = (Number) x;
					x = (long) (Math.floor(num.doubleValue() / interval) * interval);
				} else {
					// interval is omitted
					System.err
							.println("[Warning] SumCalculation.produce: interval is omitted");
				}
			}
			if (lastX.equals(x)) {
				y += ((Number) yField.select(row)).doubleValue();
//				if (y > 10000)
//					stops.size();
				++count;
			} else {
				if (count > 0)
					stops.add(new LineStop(lastX, y * times, count));
//				if (y > 10000)
//					stops.size();
				y = ((Number) yField.select(row)).doubleValue();
				count = 1;
			}
			lastX = x;
			//lastY = y;
		}
		if (count > 0)
			stops.add(new LineStop(lastX, y * times, count));
		return stops;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
