#!/bin/bash

echo "1. 🚀 BootJar"

WORKING_DIR=$(pwd)
if [[ "$WORKING_DIR" == *"application/wypl-image/script"* ]]; then
    cd ../../..
    echo "Moved to $(pwd)"
fi

chmod +x gradlew
./gradlew clean application:wypl-image:bootJar

JAR_FILE=$(find application/wypl-image/build/libs -type f -name "*.jar" | head -n 1)
echo "2. 🎯 Target JAR: $JAR_FILE"

PROJECT_NAME=$(basename "$JAR_FILE" | cut -d '-' -f 1)
IMAGE_NAME=$(basename "$JAR_FILE" | cut -d '-' -f 2)
IMAGE_TAG=$(basename "$JAR_FILE" | cut -d '-' -f 3 | cut -d '.' -f 1-3)

echo "3. 🐬 Docker Image Build, Version: $PROJECT_NAME/$IMAGE_NAME:$IMAGE_TAG"
docker build -t "$PROJECT_NAME/$IMAGE_NAME":"$IMAGE_TAG" ./application/wypl-image

export PROJECT_NAME IMAGE_NAME IMAGE_TAG