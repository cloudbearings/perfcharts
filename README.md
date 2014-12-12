Chart Generation Tool
=====================

Introduction
------------
The Chart Generation Tool is a free software written in Java, which reads logs and result data from other applications and produces charts for further analysis. It is designed for general purpose, in other words, it can generates any kinds of line and bar charts from any kind of data with appropriate extensions, but it is specially optimized for our Perf-test business.

For our perf-test business, you can get analysis charts by just putting a Jmeter result file (.jtl), some NMON resource monitoring logs (.nmon), and CPU load monitoring logs (.load) into a directory then just running the tool.  This tool make it possible to enable automation of our perf-test progress.

System Requirements
-------------------
### Operating System ###
* Unix-like operating systems
* Windows with [Cygwin](https://www.cygwin.com/)

###Java Runtime ###
* Java Runtime 1.7 or latter (<http://www.oracle.com/technetwork/java/javase/downloads/index.html>)

Components
----------
The tool contains three main components: Parser, Generator, and Driver Program.

* _Parser_ reads raw data, extracts the part of data we are interested in, and produces data table (in CSV format).
* _Generator_ generates charts from data tables according to configuration files that is defined by users and specifies which charts should be produced.
* _Driver Program_ controls the overall progress of running.

Build
-----------
1. This software uses [Apache Ant](http://ant.apache.org/) for compiling and packaging. Please install Apache Ant first.

- for RHEL/CentOS/Fedora users
		
		sudo yum install ant

- for Debian/Ubuntu users
		
		sudo aptitude install ant

2. Enter src directory, and build.

		cd src
		ant

Steps to Generate Perf-test Charts
----------------------------------
### Use cgt-perf ###
We provide a shell script (cgt-perf.sh) to simplify the process for perf-test chart generation.

	Usage:
		cgt-perf.sh [-d OUTPUT_DIR] [-o OUTPUT_FILE] [INPUT_DIR]

#### Step 1 ####
Create a new directory, and put all related perf-test logs into the directory. 

You can have a single Jmeter XML log (.jtl) and several resource monitoring logs for input. 

* The log file from NMON should be named as "servername_date_time.nmon" (without quotes), like "errata-web_140707_1258.nmon". 

* CPU load logs should be named as "servername_date_time.load" (without quotes), like "errata-web_140707_1258.load". 

A complete example is in examples/perf-test/.

#### Step 2 ####
Enter the directory you just created, and run cgt-perf.sh in bin/.

	cgt-perf.sh

Or you can run cgt-perf.sh INPUT_DIR to indicate the input directory manually:

	cgt-perf.sh /home/cgt/input/

All generated files will be placed into the sub-directory "report" in your input directory. This is the default location for storing all output files. To specify another output directory, use -d option:

	cgt-perf.sh -d home/cgt/output/ /home/cgt/input/

The file "report.html" in output directory is what you need. To specify another name of generated report, use -o option:

	cgt-perf.sh -d home/cgt/output/ /home/cgt/input/ -o my_report.html

### Advanced usage ###

#### Step 1 Run appropriate parsers ####
The parsers will convert your raw data to data table. All predefined parser reads raw data from standard input stream and put the result into standard output stream.

	java -jar lib/chartgeneration-parser-jmeter.jar < example.jtl > jmeter.csv
	java -jar lib/chartgeneration-parser-nmon.jar < errata-web_140707_0000.nmon > errata-web.csv
	java -jar lib/chartgeneration-parser-cpuload.jar < errata-web_140707_0000.load >> errata-web.csv

#### Step 2: Run Generator to produce charts ####
The generator reads a data table (CSV file) from standard input stream and put the result into standard output stream by default.

	Usage: java -jar lib/chartgeneration.jar <configuration_file>

The configuration file defines which charts should be generated for the target report. We have provides some templates in shared/ for perf-test business.

	java -jar lib/chartgeneration.jar shared/jmeter.conf < jmeter.csv > jmeter.json
	java -jar lib/chartgeneration.jar shared/res.conf < errata-web.csv > res.json

The instructions to providing your own configuration files are out of the section, and will be presented in a separated document.

#### Step 3: Show charts in web pages ####
The data of generated charts will be stored into JSON files, which is optimized for JQuery Flot plotting library (see http://www.flotcharts.org/ ). You can load these JSON files into your web pages, and customize the format of your report as you want.

Here is the format of output JSON:

	{charts: [ {
		title: <title of your chart>,
		subtitle: <subtitle of your chart>,
		xLabel: <the label of x-axis>,
		yLabel: <the label of y-axis>,
		xaxisMode: "NUMBER" | "TIME" | "CATEGORIES", /* The mode of x-axis */
		series: <The series of the chart, which Flot use to plot charts>
		}, ...
	]}

The detailed guidelines for customize reports is out of the section, and will be discussed in a separated document.
