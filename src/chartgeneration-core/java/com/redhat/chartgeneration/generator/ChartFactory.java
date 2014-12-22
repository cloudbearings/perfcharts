package com.redhat.chartgeneration.generator;
import com.redhat.chartgeneration.config.ChartConfig;
import com.redhat.chartgeneration.formatter.ChartFormatter;
import com.redhat.chartgeneration.generator.Generator;
import com.redhat.chartgeneration.report.Chart;


public interface ChartFactory <T extends Chart> {
	public  Generator createGenerator(ChartConfig<T> config) throws Exception;
	public ChartFormatter<T> createFormatter() throws Exception;
}
