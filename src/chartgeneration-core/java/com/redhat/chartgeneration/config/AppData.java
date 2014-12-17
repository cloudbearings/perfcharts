package com.redhat.chartgeneration.config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppData {
	private String cgt_home;
	private String cgt_lib;
	private String cgt_log;
	private Logger logger;
	private static AppData instance = new AppData();

	public AppData() {
		cgt_home = System.getenv("CGT_HOME");
		if (cgt_home == null) {
			try {
				cgt_home = new File(AppData.class.getProtectionDomain()
						.getCodeSource().getLocation().toURI()).getParentFile()
						.getParentFile().getAbsolutePath();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cgt_lib = System.getenv("CGT_LIB");
		if (cgt_lib == null) {
			cgt_lib = cgt_home + File.separator + "lib";
		}
		cgt_log = System.getenv("CGT_LOG");
		if (cgt_log == null) {
			cgt_log = cgt_home + File.separator + "log";
		}
		logger = Logger.getLogger("com.redhat.chartgeneration.generator");
		String logLevel = System.getProperty("logLevel");
		if (logLevel != null && !logLevel.isEmpty()) {
			logger.setLevel(Level.parse(logLevel));
		} else {
			logger.setLevel(Level.WARNING);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String logFileName = cgt_log + File.separator + "generator_"
				+ sdf.format(new Date()) + ".log";
		try {
			new File(cgt_log).mkdirs();
			FileHandler handler = new FileHandler(logFileName);
			logger.addHandler(handler);
			// logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.config("CGT_HOME=" + cgt_home);
		logger.config("CGT_LIB=" + cgt_lib);
		logger.config("CGT_LOG=" + cgt_log);
		logger.config("The log file is at '" + logFileName + "'");
	}

	public static AppData getInstance() {
		return instance;
	}

	public String getCgt_home() {
		return cgt_home;
	}

	public void setCgt_home(String cgt_home) {
		this.cgt_home = cgt_home;
	}

	public String getCgt_lib() {
		return cgt_lib;
	}

	public void setCgt_lib(String cgt_lib) {
		this.cgt_lib = cgt_lib;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
