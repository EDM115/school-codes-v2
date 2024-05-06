#!/bin/bash

echo $GITPOD_WORKSPACE_URL
docker build -t rhfront --build-arg GITPOD_WORKSPACE_URL .