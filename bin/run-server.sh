#!/bin/bash

BASE_DIR=$(dirname $0)

java -jar $BASE_DIR/server.jar $@ --spring.config.location=$BASE_DIR/application.properties