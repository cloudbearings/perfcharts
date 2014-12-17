package com.redhat.chartgeneration.generator;

import com.redhat.chartgeneration.config.StatTableConfig;
import com.redhat.chartgeneration.report.StatTable;

public class StatTableGenerator implements Generator {
	private StatTableConfig config;
	//private LineConfigGenerator cfgGenerator;
	public StatTableGenerator(StatTableConfig config) {
		this.config = config;
	}

	@Override
	public StatTable generate(PerfLog log) throws Exception {

		
		StatTable table = new StatTable(config.getTitle(),
				config.getSubtitle(), null, null);
		return table;
	}

	public StatTableConfig getConfig() {
		return config;
	}

	public void setConfig(StatTableConfig config) {
		this.config = config;
	}

}
