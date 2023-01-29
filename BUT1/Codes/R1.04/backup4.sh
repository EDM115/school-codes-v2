#!/bin/bash

set -x
user=$(whoami)
input="/home/$(user)"
output="/tmp/${user}_home_${date +%Y%m%d%H%M}.tar.gz"

function number_of_files {
	files=$(find "$input" -type f | wc -l)
	echo "Number of files : $files"
}

function number_of_directories {
	directories=$(find "$input" -type d | wc -l)
	echo "Number of directories : $directories"
}

number_of_files
number_of_directories

function create_archive {
	tar -czf $output $input
}
create_archive