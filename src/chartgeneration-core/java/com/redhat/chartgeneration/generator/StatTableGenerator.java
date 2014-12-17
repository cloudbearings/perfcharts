package com.redhat.chartgeneration.generator;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.redhat.chartgeneration.config.LineConfigGenerator;
import com.redhat.chartgeneration.config.GraphLineConfig;
import com.redhat.chartgeneration.config.GraphLineConfigRule;
import com.redhat.chartgeneration.config.StatChartConfig;
import com.redhat.chartgeneration.config.StatTableConfig;
import com.redhat.chartgeneration.report.StatTable;

public class StatTableGenerator implements Generator {
	private StatTableConfig config;
	private LineConfigGenerator cfgGenerator;
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
