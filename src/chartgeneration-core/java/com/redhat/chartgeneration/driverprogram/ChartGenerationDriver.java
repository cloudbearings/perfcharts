package com.redhat.chartgeneration.driverprogram;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import com.redhat.chartgeneration.config.LineGraphConfig;
import com.redhat.chartgeneration.config.ReportConfig;
import com.redhat.chartgeneration.config.ReportConfigLoader;
import com.redhat.chartgeneration.configtemplate.ChartConfigTemplate;
import com.redhat.chartgeneration.formatter.FlotChartFormatter;
import com.redhat.chartgeneration.generator.ReportGenerator;
import com.redhat.chartgeneration.graph.GraphReport;
import com.redhat.chartgeneration.report.ReportWritter;

public class ChartGenerationDriver {

	public static void main(String[] args) {
		try {
			//AppConfig.getInstance();
			if (args.length < 1) {
				System.err.println("Usage: \n<config file>");
				return;
			}
			String configFilePath = args[0];

			ReportConfigLoader templateLoader = new ReportConfigLoader();
			ReportConfig config = templateLoader.load(configFilePath);

			List<LineGraphConfig> lineGraphConfigs = new ArrayList<LineGraphConfig>();
			for (ChartConfigTemplate template : config.getConfigTemplate()) {
				lineGraphConfigs.add(template.generateGraphConfig());
			}

			InputStream in = config.getInputFile() == null
					|| config.getInputFile().isEmpty() ? System.in
					: new FileInputStream(config.getInputFile());
			OutputStream out = config.getOutputFile() == null
					|| config.getOutputFile().isEmpty() ? System.out
					: new FileOutputStream(config.getOutputFile());

			ReportGenerator generator = new ReportGenerator(lineGraphConfigs);
			GraphReport report = generator.generate(in);
			if (config.getTitle() != null && !config.getTitle().isEmpty())
				report.setTitle(config.getTitle());

			ReportWritter reportWritter = new ReportWritter(
					new FlotChartFormatter());
			reportWritter.produce(report, out);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
