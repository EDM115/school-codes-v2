#!/bin/bash
echo "Installing OpenJDK 17..."

# Add the OpenJDK PPA repository if it's not already added
if ! grep -q '^deb .*openjdk-r/ppa' /etc/apt/sources.list /etc/apt/sources.list.d/*; then
    sudo add-apt-repository -y ppa:openjdk-r/ppa
    sudo apt-get update
fi

sudo apt-get install -y openjdk-17-jdk

# Set up JAVA_HOME and PATH
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" | sudo tee /etc/profile.d/jdk17.sh
echo "export PATH=\$PATH:\$JAVA_HOME/bin" | sudo tee -a /etc/profile.d/jdk17.sh

