#!/bin/bash

user=$(whoami)
file="$user$str"
tar -czf /tmp/$file.tar.gz /home/$user