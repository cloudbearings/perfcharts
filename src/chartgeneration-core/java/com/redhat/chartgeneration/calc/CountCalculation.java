package com.redhat.chartgeneration.graphcalc;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.report.GraphPoint;

public class CountCalculation implements GraphCalculation {
	private int interval = 0;
	private double times = 1;
	private boolean setZeroIfIntervalNoData = false;

	public CountCalculation() {

	}

	public CountCalculation(int interval) {
		this.interval = interval;
	}

	public CountCalculation(int interval, double times) {
		this.interval = interval;
		this.times = times;
	}

	public CountCalculation(int interval, double times,
			boolean setZeroIfIntervalNoData) {
		this.interval = interval;
		this.times = times;
		this.setZeroIfIntervalNoData = setZeroIfIntervalNoData;
	}

	public List<GraphPoint> produce(List<List<Object>> rows,
			FieldSelector xField, FieldSelector yField) {
		List<GraphPoint> stops = new LinkedList<GraphPoint>();
		long lastX = 0;
		int count = 0;
		for (Iterator<List<Object>> it = rows.iterator(); it.hasNext();) {
			List<Object> row = it.next();
			Object rawX = xField.select(row);
			long x = 0;

			if (interval > 1) {
				if (Number.class.isAssignableFrom(rawX.getClass())) {
					Number num = (Number) rawX;
					x = num.longValue() / interval * interval;
				} else {
					Number num = (Number) rawX;
					x = num.longValue();
					System.out
							.println("Warning: CountCalculation.produce: cannot merge");
				}
			} else {
				Number num = (Number) rawX;
				x = num.longValue();
			}

			if (x == lastX) {
				++count;
			} else {
				if (count > 0) {
					stops.add(new GraphPoint(lastX, count * times, count));
					if (setZeroIfIntervalNoData && lastX + interval < x) {
						stops.add(new GraphPoint(lastX + interval, 0, 0));
						if (lastX + interval < x - interval)
							stops.add(new GraphPoint(x - interval, 0, 0));
					}
				}
				count = 1;
			}
			lastX = x;
		}
		if (count > 0)
			stops.add(new GraphPoint(lastX, count * times, count));
		return stops;
	}

	public double getTimes() {
		return times;
	}

	public void setTimes(double times) {
		this.times = times;
	}

	public boolean isSetZeroIfIntervalNoData() {
		return setZeroIfIntervalNoData;
	}

	public void setSetZeroIfIntervalNoData(boolean setZeroIfIntervalNoData) {
		this.setZeroIfIntervalNoData = setZeroIfIntervalNoData;
	}
	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}
}
