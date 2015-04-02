#/bin/bash

cd `dirname $0`

chartgeneration-perftest/build_template_report.sh report.html
chartgeneration-perftest/build_mono_template_report.sh report_mono.html

ant && chmod +x ../bin/*



