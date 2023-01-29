#!/bin/bash

user=$(whoami)
output="/tmp/${user}_home_${date +%Y%m%d%H%M}.tar.gz"
tar -czf $output /home/$user
ls -l $output