#!/bin/bash
INPUT=$(grep -E 'version[[:space:]]*\=[[:space:]]*' build.gradle)
RELEASEVERSION=$(echo $INPUT | cut -d "=" -f 2 | cut -d "'" -f 2)

echo v$RELEASEVERSION
