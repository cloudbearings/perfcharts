package com.redhat.chartgeneration.calc;

import java.util.LinkedList;
import java.util.List;

import com.redhat.chartgeneration.chart.Point2D;
import com.redhat.chartgeneration.common.FieldSelector;

/**
 * This calculation groups data rows by their x-fields, counts the number of
 * them, and makes the count as result y-values.
 * 
 * @author Rayson Zhu
 *
 */
public class CountCalculation implements Chart2DCalculation {
	/**
	 * the interval for point merging
	 */
	private int interval = 0;
	/**
	 * the times to multiply on y-value of each point
	 */
	private double times = 1;
	/**
	 * whether set y-value to zero when there is no data in that interval
	 */
	private boolean setZeroIfIntervalNoData = false;

	/**
	 * constructor
	 */
	public CountCalculation() {

	}

	/**
	 * constructor
	 * 
	 * @param interval
	 *            the interval for point merging
	 */
	public CountCalculation(int interval) {
		this.interval = interval;
	}

	/**
	 * constructor
	 * 
	 * @param interval
	 *            the interval for point merging
	 * @param times
	 *            the times to multiply on y-value of each point
	 */
	public CountCalculation(int interval, double times) {
		this.interval = interval;
		this.times = times;
	}

	/**
	 * constructor
	 * 
	 * @param interval
	 *            the interval for point merging
	 * @param times
	 *            the times to multiply on y-value of each point
	 * @param setZeroIfIntervalNoData
	 *            whether set y-value to zero when there is no data in that
	 *            interval
	 */
	public CountCalculation(int interval, double times,
			boolean setZeroIfIntervalNoData) {
		this.interval = interval;
		this.times = times;
		this.setZeroIfIntervalNoData = setZeroIfIntervalNoData;
	}

	public List<Point2D> produce(List<List<Object>> rows, FieldSelector xField,
			FieldSelector yField) {
		List<Point2D> stops = new LinkedList<Point2D>();
		if (rows.isEmpty())
			return stops;
		Number firstX = (Number) xField.select(rows.get(0));
		Number lastX = 0;
		int count = 0;
		for (List<Object> row : rows) {
			Number x = (Number) xField.select(row);
			if (interval > 1) {
				x = firstX.longValue() + (x.longValue() - firstX.longValue())
						/ interval * interval;
			}
			if (lastX.equals(x)) {
				++count;
			} else {
				if (count > 0) {
					stops.add(new Point2D(lastX, count * times, count));
					if (setZeroIfIntervalNoData
							&& lastX.doubleValue() + interval < x.doubleValue()) {
						stops.add(new Point2D(lastX.doubleValue() + interval,
								0, 0));
						if (lastX.doubleValue() + interval < x.doubleValue()
								- interval)
							stops.add(new Point2D(x.doubleValue() - interval,
									0, 0));
					}
				}
				count = 1;
			}
			lastX = x;
		}
		if (count > 0)
			stops.add(new Point2D(lastX, count * times, count));
		return stops;
	}

	/**
	 * Get the times to multiply on y-value of each point.
	 * 
	 * @return the times
	 */
	public double getTimes() {
		return times;
	}

	/**
	 * Set the times to multiply on y-value of each point.
	 * 
	 * @param times
	 *            the times
	 */
	public void setTimes(double times) {
		this.times = times;
	}

	/**
	 * Determine whether set y-value to zero when there is no data in that
	 * interval.
	 * 
	 * @return true if yes, otherwise false
	 */
	public boolean isSetZeroIfIntervalNoData() {
		return setZeroIfIntervalNoData;
	}

	/**
	 * Specify whether set y-value to zero when there is no data in that
	 * interval.
	 * 
	 * @param setZeroIfIntervalNoData
	 *            true if yes, otherwise false
	 */
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
