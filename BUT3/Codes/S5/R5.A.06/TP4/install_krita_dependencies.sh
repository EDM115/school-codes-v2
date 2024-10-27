#!/bin/bash

# Change to the test directory
cd /home/appimage/depstest

# Create a virtual environment in the test directory
python3 -m venv krita_env

# Activate the virtual environment
source krita_env/bin/activate

# Upgrade pip and install necessary packages
pip install --upgrade pip
pip install numpy opencv-python-headless

# Deactivate the virtual environment
deactivate

# Define the destination path for Krita's Python environment site-packages
KRITA_SITE_PACKAGES_PATH="/home/appimage/appimage-workspace/deps/usr/lib/python3.10/site-packages"

# Move installed packages to Krita's site-packages
cp -r krita_env/lib/python3.10/site-packages/numpy* $KRITA_SITE_PACKAGES_PATH
cp -r krita_env/lib/python3.10/site-packages/cv2* $KRITA_SITE_PACKAGES_PATH

# Clean up
rm -rf krita_env

# Confirm installation
echo "Dependencies installed and moved to Krita's site-packages."
