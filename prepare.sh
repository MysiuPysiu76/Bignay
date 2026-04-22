#!/bin/bash

rm -rf build

./gradlew :fabric:runClient &
PROCESS=$!
sleep 15
kill $PROCESS

./gradlew :forge:runClient &
PROCESS=$!
sleep 15
kill $PROCESS

./gradlew clean build

rm -fr build/*

cp fabric/build/libs/*-fabric.jar build
cp forge/build/libs/*-forge.jar build

rm -fr fabric/run
rm -fr forge/run
