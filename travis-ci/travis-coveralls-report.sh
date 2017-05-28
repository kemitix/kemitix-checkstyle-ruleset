#!/usr/bin/env bash

./mvnw --projects builder,plugin test jacoco:report coveralls:report
