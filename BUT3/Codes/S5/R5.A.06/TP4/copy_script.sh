#!/bin/bash

# Define container name
CONTAINER_NAME=krita-auto-1

# Path to the dependency installation script + extension
SCRIPT_PATH=./install_krita_dependencies.sh
EXTENSION_PATH=./AutoLineColoring.zip

# Copy install script to the Docker container
docker exec -it "$CONTAINER_NAME" mkdir -p /home/appimage/depstest
docker cp "$SCRIPT_PATH" "$CONTAINER_NAME:/home/appimage/depstest/install_krita_dependencies.sh"
docker cp "$EXTENSION_PATH" "$CONTAINER_NAME:/home/appimage/AutoLineColoring.zip"

echo "install_krita_dependencies.sh copied to $CONTAINER_NAME:/home/appimage/depstest"
echo "AutoLineColoring.zip copied to $CONTAINER_NAME:/home/appimage"
