#!/bin/bash

PROJECT=simple-cab

BASE_DIR=$(dirname $0)

set -e

docker-compose -p $PROJECT -f ./$BASE_DIR/docker-compose.yml down --rmi 'local' --volumes --remove-orphans

docker-compose -p $PROJECT -f ./$BASE_DIR/docker-compose.yml up