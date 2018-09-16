#!/bin/bash
source $(dirname $0)/env.sh
cd $PROJECT_ROOT
for pjt in $PROJECT_APP
do
gradle :${pjt}:bootJar
done
cd -
