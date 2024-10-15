#!/bin/bash

echo "🚀 Navigating to Git root directory..."
GIT_ROOT=$(git rev-parse --show-toplevel)

if [ $? -ne 0 ]; then
  echo "❌ Error: Not a Git repository."
  exit 1
fi
cd "$GIT_ROOT" || { echo "❌ Failed to change directory to Git root."; exit 1; }

echo "📦 Start Submodule Update"

git submodule update --remote --merge
git add .

echo '🎯 End Submodule Update'