#!/usr/bin/env bash

./mvnw --projects builder,checks,plugin test jacoco:report coveralls:report
