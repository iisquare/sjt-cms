#!/bin/bash
export JAVA_HOME=/opt/jdk1.8.0_181
export JRE_HOME=$JAVA_HOME/jre
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin:$JRE_HOME/bin
export PROJECT_ROOT=$(dirname $(dirname $(readlink -f $0)))
export PROJECT_APP=("manage" "cms-web" "cms-wap")
