#!/bin/bash
docker build . -t demo-native
mkdir -p build
docker run --rm --entrypoint cat demo-native  /home/application/function.zip > build/function.zip
