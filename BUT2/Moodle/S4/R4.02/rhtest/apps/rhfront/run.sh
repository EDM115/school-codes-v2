#!/bin/bash

rm .env
echo VITE_GITPOD_WORKSPACE_URL=$GITPOD_WORKSPACE_URL > .env
