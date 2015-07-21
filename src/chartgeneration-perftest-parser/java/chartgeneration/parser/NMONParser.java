package chartgeneration.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The parser converts NMON monitoring logs to data tables (in CSV format).
 *
 * @author Rayson Zhu
 */
public class NMONParser implements DataParser {
    public void parse(InputStream in, OutputStream out) throws IOException,
            ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String lineStr;
        List<String[]> lines = new LinkedList<String[]>();
        Map<Integer, Long> timeTable = new HashMap<Integer, Long>(16000);
        //Map<String, String> metaInfo = new HashMap<String, String>(30);
        // TimeZone utcZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat timeFormat = new SimpleDateFormat("H:m:s d-MMM-y");
        // timeFormat.setTimeZone(utcZone);

        final Map<String, Integer> diskColumnMap = new HashMap<String, Integer>(4);
        //Pattern diskPattern = Pattern.compile("^[hsv]d[a-z]$");

        Date startTime = Settings.getInstance().getStartTime();
        Date endTime = Settings.getInstance().getEndTime();

        while ((lineStr = reader.readLine()) != null) {
            String[] extractedLine = lineStr.split(",");
            if (extractedLine.length == 0)
                continue;
            lines.add(extractedLine);
            if (extractedLine[0].equals("ZZZZ") && extractedLine.length >= 4) {
                int tsLabelValue = Integer.parseInt(extractedLine[1]
                        .substring(1));
                Date date = timeFormat.parse(extractedLine[2] + " "
                        + extractedLine[3]);
                if (startTime != null && date.before(startTime)
                        || endTime != null && date.after(endTime))
                    continue;
                timeTable.put(tsLabelValue, date.getTime());
            } else if (extractedLine[0].startsWith("DISK")
                    && extractedLine.length > 2
                    && !extractedLine[1].startsWith("T")) {
                for (int i = 2; i < extractedLine.length; ++i) {
                    //if (diskPattern.matcher(extractedLine[i]).matches()) {
                    // diskColumnMap.putIfAbsent(extractedLine[i], i);
                    diskColumnMap.put(extractedLine[i], i);
                    //}
                }
            }/* else if (extractedLine[0].equals("AAA")
                    && extractedLine.length >= 3) {
				metaInfo.put(extractedLine[1], extractedLine[2]);
			}*/

        }
        in.close();

        Map<String, NMONItemParser> keyParserMap = new HashMap<>();

        keyParserMap.put("CPU_ALL", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                writer.printRecord("CPU",
                        ts.toString(),
                        Float.toString(Float.parseFloat(line[2])),
                        Float.toString(Float.parseFloat(line[3])),
                        Float.toString(Float.parseFloat(line[4])),
                        String.format("%.1f", 100.0f - Float.parseFloat(line[5])),
                        Integer.toString(Integer.parseInt(line[7]))
                );
            }
        });

        keyParserMap.put("MEM", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                writer.printRecord("MEM",
                        ts.toString(),
                        Float.toString(Float.parseFloat(line[2])),
                        Float.toString(Float.parseFloat(line[6])),
                        Float.toString(Float.parseFloat(line[11])),
                        Float.toString(Float.parseFloat(line[14]))
                );
            }
        });

        keyParserMap.put("VM", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                writer.printRecord("VM",
                        ts.toString(),
                        Float.toString(Float.parseFloat(line[10])),
                        Float.toString(Float.parseFloat(line[11]))
                );
            }
        });

        keyParserMap.put("NET", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                int numberOfNetworkAdapters = (line.length - 2) >> 1;
                float read = 0;
                float write = 0;
                for (int i = 0; i < numberOfNetworkAdapters; ++i) {
                    read += Float.parseFloat(line[2 + i]);
                    write += Float.parseFloat(line[2 + numberOfNetworkAdapters + i]);
                }
                writer.printRecord("NET",
                        ts.toString(),
                        Float.toString(read),
                        Float.toString(write)
                );
            }
        });

        keyParserMap.put("DISKBUSY", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                final String tsStr = ts.toString();
                for (String disk : diskColumnMap.keySet()) {
                    Integer index = diskColumnMap.get(disk);
                    if (index == null)
                        continue;
                    writer.printRecord("DISKBUSY-" + disk,
                            tsStr,
                            Float.toString(Float.parseFloat(line[index]))
                    );
                }
            }
        });

        keyParserMap.put("DISKREAD", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                final String tsStr = ts.toString();
                for (String disk : diskColumnMap.keySet()) {
                    Integer index = diskColumnMap.get(disk);
                    if (index == null)
                        continue;
                    writer.printRecord("DISKREAD-" + disk,
                            tsStr,
                            Float.toString(Float.parseFloat(line[index]))
                    );
                }
            }
        });

        keyParserMap.put("DISKWRITE", new NMONItemParser() {
            @Override
            public void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException {
                Long ts = timeTable.get(Integer.parseInt(line[1].substring(1)));
                if (ts == null)
                    return;
                final String tsStr = ts.toString();
                for (String disk : diskColumnMap.keySet()) {
                    Integer index = diskColumnMap.get(disk);
                    if (index == null)
                        continue;
                    writer.printRecord("DISKWRITE-" + disk,
                            tsStr,
                            Float.toString(Float.parseFloat(line[index]))
                    );
                }
            }
        });

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        for (String[] line : lines) {
            if (line.length < 2 || !line[1].startsWith("T"))
                continue;
            NMONItemParser nmonItemParser = keyParserMap.get(line[0]);
            if (nmonItemParser != null)
                nmonItemParser.parse(csvPrinter, line, timeTable);
        }
        csvPrinter.flush();
    }

    private interface NMONItemParser {
        void parse(CSVPrinter writer, String[] line, Map<Integer, Long> timeTable) throws IOException,
                ParseException;
    }
}
