package chartgeneration.tool.zabbix.downloader;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.logging.Logger;

import chartgeneration.tool.zabbix.downloader.ZabbixDownloader.ZabbixAPIException;
import chartgeneration.tool.zabbix.downloader.ZabbixDownloader.ZabbixAPIFailureException;

public class ZabbixDownloaderEntry {
	private final static Logger LOGGER = Logger
			.getLogger(ZabbixDownloaderEntry.class.getName());

	private final static SimpleDateFormat sdf = new SimpleDateFormat(
			"y-M-d h:m:s");

	public static void main(String[] args) throws IOException, ParseException,
			ZabbixAPIException {
		if (args.length < 1) {
			System.err.println("Usage:<OUTPUT_DIR>");
			Runtime.getRuntime().exit(1);
			return;
		}
		String outputDir = args[0];

		// load configuration
		final Properties prop = System.getProperties();
		final String apiUrl = prop.getProperty("API_URL");
		if (apiUrl == null || apiUrl.isEmpty())
			throw new InvalidParameterException("API_URL is required.");
		// final String ca = prop.getProperty("CA");
		final String user = prop.getProperty("user");
		if (user == null || user.isEmpty())
			throw new InvalidParameterException("user is required.");

		final String hostStr = prop.getProperty("hosts");
		if (hostStr == null || hostStr.isEmpty()) {
			throw new InvalidParameterException("hosts is required.");
		}
		String[] hosts = hostStr.split(";");

		System.out.println("Zabbix password for user '" + user + "' on '" + apiUrl + "':");

		String password;
		Console console = System.console();
		if (console != null)
			password = new String(System.console().readPassword());
		else {
			Scanner scanner = new Scanner(System.in);
			password = scanner.nextLine();
			scanner.close();
		}
		if (password == null || password.isEmpty())
			throw new InvalidParameterException("password is required.");

		TimeZone timeZone;
		final String timeZoneStr = prop.getProperty("time_zone");
		if (timeZoneStr == null || timeZoneStr.isEmpty())
			timeZone = TimeZone.getTimeZone(timeZoneStr);
		else
			timeZone = TimeZone.getTimeZone("UTC");
		TimeZone.setDefault(timeZone);
		LOGGER.info("The fallback time zone is " + timeZone.getDisplayName());

		final String startTimeStr = prop.getProperty("start_time");
		Date startTime = null;
		if (startTimeStr != null && !startTimeStr.isEmpty()) {
			startTime = sdf.parse(startTimeStr);
			LOGGER.info("Use start time " + startTime.toString());
		}

		final String endTimeStr = prop.getProperty("end_time");
		Date endTime = null;
		if (endTimeStr != null && !endTimeStr.isEmpty()) {
			endTime = sdf.parse(endTimeStr);
			LOGGER.info("Use start time " + endTime.toString());
		}

		// make sure output directory exists
		new File(outputDir).mkdirs();

		ZabbixDownloader downloader = new ZabbixDownloader(apiUrl, user,
				password, hosts, startTime, endTime, outputDir);
		downloader.download();
	}
}
