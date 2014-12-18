package com.redhat.chartgeneration.graphcalc;

import java.util.List;

import com.redhat.chartgeneration.common.FieldSelector;
import com.redhat.chartgeneration.report.LineStop;

public interface GraphCalculation {
	public List<LineStop> produce(List<List<Object>> rows,
			FieldSelector xField, FieldSelector yField);

	public int getInterval();

	public void setInterval(int interval);
}
