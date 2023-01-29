#!/bin/bash

while read line
do
	uid=$(echo "$line" | cut -d: -f3)

	if [ "$uid" -gt 100 ]
	then
		login=$(echo "$line" | cut -d: -f1)

		echo "$login"
	fi
done < /etc/passwd