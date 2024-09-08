#!/bin/bash

echo "1. 🚀 BootJar"

WORKING_DIR=$(pwd)
if [[ "$WORKING_DIR" == *"application/wypl-image"* ]]; then
    cd ../..
    echo "Moved to $(pwd)"
fi

./gradlew clean application:wypl-image:bootJar

JAR_FILE=$(find application/wypl-image/build/libs -type f -name "*.jar" | head -n 1)
echo "2. 🎯 Target JAR: $JAR_FILE"

PROJECT_NAME=$(basename "$JAR_FILE" | cut -d '-' -f 1)
IMAGE_NAME=$(basename "$JAR_FILE" | cut -d '-' -f 2)
IMAGE_TAG=$(basename "$JAR_FILE" | cut -d '-' -f 3 | cut -d '.' -f 1-3)

echo "$PROJECT_NAME:$IMAGE_NAME:$IMAGE_TAG"

echo "3. 🐬 Docker Image Build, Version: $PROJECT_NAME/$IMAGE_NAME:$IMAGE_TAG"
docker build -t "$PROJECT_NAME/$IMAGE_NAME":"$IMAGE_TAG" ./application/wypl-image

echo "4. 🚀 Docker Container Start, Version: $PROJECT_NAME/$IMAGE_NAME:$IMAGE_TAG"
CONTAINER_NAME="$PROJECT_NAME-$IMAGE_NAME-$IMAGE_TAG-server"
CONTAINER_ID=$(docker ps -q --filter "name=$CONTAINER_NAME")

if [ -n "$CONTAINER_ID" ]; then
    docker rm -f "$CONTAINER_ID"
fi

docker run -d \
  --name "$CONTAINER_NAME"  \
  -e PROFILE=default  \
  -p 8080:8080  \
  "$PROJECT_NAME/$IMAGE_NAME":"$IMAGE_TAG"