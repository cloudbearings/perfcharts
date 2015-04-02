#!/bin/bash
cd `dirname "$0"` && cd res

if [ "$#" -lt 1 ]; then
	echo 'Build Template Report File'
	echo '	'`basename "$0"` '<OUTPUT_HTML_FILE>'
	exit 1
fi

included_scripts=('lib/jquery-1.11.1.js' 'lib/jquery.tablesorter.js' 'lib/jquery.stickytableheaders.js' 'lib/flot/jquery.flot.js lib/flot/jquery.flot.time.js' 'lib/flot/jquery.flot.categories.js' 'lib/flot/jquery.flot.selection.js' 'lib/flot/jquery.flot.crosshair.js' 'lib/flot/jquery.flot.axislabels.js' 'js/perfcharts.js' 'data.js')
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
	echo "<script type=\"text/javascript\" src=\"$js\"></script>" >> "$output_file"
done

# included css files
for css in ${included_stylesheets[@]}; do
	echo "<link rel=\"stylesheet\" type=\"text/css\" href=\"$css\" />" >> "$output_file"
done

echo '</head>' >> "$output_file"

# body
echo '<body>' >> "$output_file"
for html in ${included_htmls[@]}; do
	cat "$html" >> "$output_file"
done
echo '</body>' >> "$output_file"
echo '</html>' >> "$output_file"

