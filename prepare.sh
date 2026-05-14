#!/bin/bash

./gradlew :fabric:runClient &
PROCESS=$!
sleep 15
kill $PROCESS

#./gradlew :forge:runClient &
#PROCESS=$!
#sleep 15
#kill $PROCESS

./gradlew :neoforge:runClient &
PROCESS=$!
sleep 15
kill $PROCESS

./gradlew clean build

rm -fr build/*

cp fabric/build/libs/*-fabric.jar build
cp forge/build/libs/*-forge.jar build
cp neoforge/build/libs/*-neoforge.jar build

rm -fr common/run
rm -fr fabric/run
rm -fr forge/run
rm -fr neoforge/run
