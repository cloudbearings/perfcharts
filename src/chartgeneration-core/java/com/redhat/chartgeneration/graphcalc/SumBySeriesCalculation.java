package com.redhat.chartgeneration.graphcalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.report.LineStop;

public class SumBySeriesCalculation implements GraphCalculation {
	private int interval = 1000;
	private int times = 1;
	private FieldSelector labelSelector;

	public SumBySeriesCalculation() {

	}

	public SumBySeriesCalculation(FieldSelector labelSelector) {
		this.labelSelector = labelSelector;
	}

	public SumBySeriesCalculation(FieldSelector labelSelector, int interval) {
		this.labelSelector = labelSelector;
		this.interval = interval;
	}

	public SumBySeriesCalculation(FieldSelector labelSelector, int interval,
			int times) {
		this.labelSelector = labelSelector;
		this.interval = interval;
		this.times = times;
	}

	public List<LineStop> produce(List<List<Object>> rows,
			FieldSelector xField, FieldSelector yField) {
		List<LineStop> stops = new LinkedList<LineStop>();
		long lastX = 0;
		Map<String, Integer> labelIndexMap = new HashMap<String, Integer>();
		List<Double> valueList = new ArrayList<Double>();
		List<Integer> countList = new ArrayList<Integer>();
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
							.println("Warning: SumCalculation.produce: cannot merge");
				}
			} else {
				Number num = (Number) rawX;
				x = num.longValue();
			}
			String label = labelSelector.select(row).toString();
			Integer indexWrapper = labelIndexMap.get(label);
			int index = indexWrapper != null ? indexWrapper.intValue() : -1;
			double value = ((Number) yField.select(row)).doubleValue();
			if (index < 0) {
				index = countList.size();
				labelIndexMap.put(label, index);
				countList.add(0);
				valueList.add(0.0);
			}
			if (x == lastX) {
				valueList.set(index, valueList.get(index) + value);
				countList.set(index, countList.get(index) + 1);
			} else {
				double y = 0.0;
				int count = 0;
				for (int i = 0; i < countList.size(); ++i) {
					int n = countList.get(i);
					if (n > 0) {
						y += valueList.get(i) / n;
						++count;
					}
				}
				if (count > 0)
					stops.add(new LineStop(lastX, y * times, count));
				valueList.set(index, value);
				countList.set(index, 1);
			}
			lastX = x;
		}
		double y = 0.0;
		int count = 0;
		for (int i = 0; i < countList.size(); ++i) {
			int n = countList.get(i);
			if (n > 0) {
				y += valueList.get(i) / n;
				++count;
			}
		}
		if (count > 0)
			stops.add(new LineStop(lastX, y * times, count));
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

	public FieldSelector getLabelSelector() {
		return labelSelector;
	}

	public void setLabelSelector(FieldSelector labelSelector) {
		this.labelSelector = labelSelector;
	}
}
