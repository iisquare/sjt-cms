#!/bin/bash
source $(dirname $0)/env.sh
for pjt in $PROJECT_APP
do
java -jar ${PROJECT_ROOT}/${pjt}/build/libs/sjt-${pjt}.jar
done

