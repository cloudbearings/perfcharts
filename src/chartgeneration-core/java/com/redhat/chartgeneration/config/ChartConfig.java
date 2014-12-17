package com.redhat.chartgeneration.config;

import com.redhat.chartgeneration.generator.Generator;

public interface ChartConfig {
	public Generator createGenerator() throws Exception;
}
