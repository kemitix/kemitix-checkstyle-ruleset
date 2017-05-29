#!/usr/bin/env bash

## Only send coveralls reports from Travis-CI. Some CIs, like Shippable, lie by setting TRAVIS=true.
## Currently, Shippable, does not set TRAVIS_LANGUAGE, but Travis-CI does.
if [ "$TRAVIS_LANGUAGE" = "java" ];then
    ./mvnw --projects builder,plugin test jacoco:report coveralls:report
fi
