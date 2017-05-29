#!/usr/bin/env bash

if [ "$TRAVIS" = "true" ];then
    ./mvnw --projects builder,plugin test jacoco:report coveralls:report
fi
