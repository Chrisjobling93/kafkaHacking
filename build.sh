#!/bin/bash

###Export the JAVA_HOME Path to use jdk
export JAVA_HOME=/etc/alternatives/java_sdk_11

###Build
./gradlew --stop &&./gradlew clean build jacocoTestReport sonarqube
