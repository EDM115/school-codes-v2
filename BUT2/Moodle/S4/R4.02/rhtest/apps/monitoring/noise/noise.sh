#!/bin/bash

while true
do
    curl rhapi:8080/api/rechercher?mode=all
    curl rhapi:8080/api/rechercher?mode=all
    curl rhapi:8080/api/rechercher?mode=all
    curl rhapi:8080/api/rechercher
    curl rhapi:8080/api/rechercher
    curl -X POST "rhapi:8080/api/ajouter?id=TEST&name=TEST&lastname=Noise&salary=12345&level=1"
    sleep 1
done