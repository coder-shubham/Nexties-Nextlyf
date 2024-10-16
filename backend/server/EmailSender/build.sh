#!/bin/bash
MVN="mvn  -Dmaven.test.skip=true"
rm -f target/dependency/* &&
$MVN package
