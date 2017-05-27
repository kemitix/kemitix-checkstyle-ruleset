#!/usr/bin/env bash

./mvnw --projects plugin,ruleset --settings travis-settings.xml -Dskip-Tests=true -P release -B deploy
