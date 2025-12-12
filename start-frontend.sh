#!/bin/bash
export SHELL=/bin/bash
export PATH=/usr/bin:/bin:$PATH

echo "Starting Frontend..."
cd /workspaces/jayna

# Start webpack directly
node node_modules/.bin/webpack serve --config webpack/webpack.common.js --mode development --env stats=normal 