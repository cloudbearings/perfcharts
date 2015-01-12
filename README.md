Perfcharts
=====================

Introduction
------------
Perfcharts is a free software written in Java, which reads performance testing and system monitoring results from Jmeter, NMON, and/or other applications to produce charts for further analysis. It can generate any line and bar chart from any kind of data with appropriate extensions, but now is specially designed for performance tesing business.

With this tool, you can get analysis charts by just putting a Jmeter result file (.jtl), some NMON resource monitoring logs (.nmon), and CPU load monitoring logs (.load) into a directory then just running the tool. This tool make it possible to enable automatic performance tesing.

System Requirements
-------------------
### Operating System ###
* Linux or other Unix-like operating systems
* Windows with [Cygwin](https://www.cygwin.com/)

###Java Runtime ###
* Java SE 1.7 or above (<http://www.oracle.com/technetwork/java/javase/downloads/index.html>)

Components
----------
The tool contains three main components: Parser, Generator, and Driver Program.

* _Parser_ reads raw data, extracts the part of data we are interested in, and produces data table (in CSV format).
* _Generator_ generates charts from data tables according to configuration files that is defined by users and specifies which charts should be produced.
* _Driver Program_ controls the overall progress of running.

Steps to Generate Perf-test Charts
----------------------------------
### Use cgt-perf ###
We provide a shell script (cgt-perf) to simplify the process for perf-test chart generation.

	Usage:
		cgt-perf [-d OUTPUT_DIR] [-o OUTPUT_FILE] [-z TIME_ZONE] [-f FROM_TIME] [-t TO_TIME] [INPUT_DIR]

#### Step 1 ####
Create a new directory, and put all related perf-test logs into the directory. 

You can have a single Jmeter XML log (.jtl) and several resource monitoring logs for input. 

* The log file from NMON should be named as "servername\[\_&lt;surfix&gt;\].nmon" (without quotes, &lt;surfix&gt; is optional), like "errata-web_140717_1258.nmon". 

* CPU load logs should be named as "servername\[\_&lt;surfix&gt;\].load", like "errata-web_140717_1258.load". You can get this kind of CPU load logs by writing a simple shell script. The format of log entry is:

		yyyy-MM-dd hh:mm:ss, 1min 5min 15min\[, cores\]

Here is a sample CPU load monitoring log:

		2014-07-17 17:12:43, 0.73 0.99 1.27, 8
		2014-07-17 17:12:48, 0.73 1.01 1.33, 8
		2014-07-17 17:12:53, 0.73 0.99 1.22, 8
		2014-07-17 17:12:58, 0.73 1.01 1.28, 8

#### Step 2 ####
Enter the directory you just created, and run cgt-perf.sh in bin/.

		cgt-perf

Or you can run cgt-perf.sh INPUT_DIR to indicate the input directory manually:

		cgt-perf /home/cgt/input/

All generated files will be placed into the sub-directory "report" of your input directory. This is the default location for storing all output files. To specify another output directory, use -d option:

		cgt-perf -d home/cgt/output/ /home/cgt/input/

The file "report.html" in output directory is what you need. To specify another name of generated report, use -o option:

		cgt-perf -d home/cgt/output/ /home/cgt/input/ -o my_report.html

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
		chmod +x ../bin/*


Release Log
-----------
* v0.3.4
1. new style of axis labels
2. other details related to the format of report

* v0.3.3
1. support for customizing the title and subtitle of composite chart
2. change the unit of response time to 'ms'
3. change the series name format in composite chart
4. automatic interval choosing improvements

* v0.3.2
1. some bugfixes
2. reorganize some code


* v0.3.1
1. new feature: support for specifying time duration
2. new feature: automatic interval choosing
3. new feature: Jmeter CSV log support
4. change: new rules for generating Jmeter charts
5. bugfix: the time zone issue
6. bugfix: incorrect calculation of summary chart when a transaction only has one sample

* v0.3.0
1. new features: summary chart for Jmeter Test logs
2. bugfix: mismatching between CPU load logs and its parser

* v0.2.0
1. add support for subtitles
2. add support for the number of cores in CPU load charts
3. rewrite the driver program in Bash shell script
4. new document
5. some bugfixes

* v0.1.0
1. first release

