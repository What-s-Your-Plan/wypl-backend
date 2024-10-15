#!/bin/bash

echo "🚀 Start Submodule Update"

git submodule update --recursive
git add .

echo '🎯 End Submodule Update'