#!/bin/bash

rm -fr build/*

cp fabric/build/libs/*-fabric.jar build
cp forge/build/libs/*-forge.jar build
