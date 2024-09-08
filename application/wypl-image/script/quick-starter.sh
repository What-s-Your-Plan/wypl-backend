#!/bin/bash

sh docker-image-build.sh

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