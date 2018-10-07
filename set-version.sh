#!/usr/bin/env bash

if test $# != 1
then
    echo "Next version missing"
    exit
fi

NEXT=$1

echo "Updating version to $NEXT..."
mvn versions:set -DnewVersion=$NEXT -DgenerateBackupPoms=false -DprocessAllModules
echo "Updating README template..."
perl -p -i -e "s,DEV-SNAPSHOT</,$NEXT</," builder/src/main/resources/README-template.md
echo "Done."
