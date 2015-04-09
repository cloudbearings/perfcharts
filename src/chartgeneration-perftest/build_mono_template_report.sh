#!/bin/bash
cd `dirname "$0"` && cd res

if [ "$#" -lt 1 ]; then
	echo 'Build Mono Report Template File'
	echo '	'`basename "$0"` '<OUTPUT_HTML_FILE>'
	exit 1
fi

java -jar ../tools/yuicompressor.jar js/perfcharts.js -o js/perfcharts.min.js

included_scripts=('lib/jquery-2.1.3.min.js' 'lib/jquery.tablesorter.min.js' 'lib/jquery.stickytableheaders.min.js' 'lib/flot/jquery.flot.min.js lib/flot/jquery.flot.time.min.js' 'lib/flot/jquery.flot.categories.min.js' 'lib/flot/jquery.flot.selection.min.js' 'lib/flot/jquery.flot.crosshair.min.js' 'lib/flot/jquery.flot.axislabels.min.js' 'js/perfcharts.min.js')
included_stylesheets=('css/tablesorter/theme.default.css' 'css/default-style.css')
included_htmls=('partial/body.html')

output_file=$1

# HTML Declaration and Headers
echo '<!DOCTYPE html>' > "$output_file"
echo '<html lang="en-US">' >> "$output_file"
echo '<head>' >> "$output_file"
echo '<meta charset="UTF-8">' >> "$output_file"
echo '<title>Perfcharts Report</title>' >> "$output_file"

# included Javascript files
for js in ${included_scripts[@]}; do
	echo '<script type="text/javascript">' >> "$output_file"
	cat "$js" >> "$output_file"
	echo '</script>' >> "$output_file"
done

# included css files
for css in ${included_stylesheets[@]}; do
	echo '<style type="text/css">' >> "$output_file"
	cat "$css" >> "$output_file"
	echo '</style>' >> "$output_file"
done

echo '</head>' >> "$output_file"

# body
echo '<body>' >> "$output_file"
for html in ${included_htmls[@]}; do
	cat "$html" >> "$output_file"
done
echo '</body>' >> "$output_file"
echo '</html>' >> "$output_file"

