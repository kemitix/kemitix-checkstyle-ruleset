#!/usr/bin/env bash

echo "TRAVIS: $TRAVIS"
set
if [ "$TRAVIS" = "true" ];then
    ./mvnw --projects builder,plugin test jacoco:report coveralls:report
fi
