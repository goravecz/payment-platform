#!/bin/bash

echo "Stopping all running containers..."
docker ps -q | xargs -r docker stop

echo "Removing matching containers..."
docker ps -a --format '{{.ID}} {{.Names}}' | awk '/payment|pga|postgre|transaction/ {print $1}' | xargs -r docker rm -f

#echo "Removing matching images..."
#docker images --format '{{.ID}} {{.Repository}}' | awk '/payment|pga|postgre/ {print $1}' | xargs -r docker rmi -f

echo "Removing matching volumes..."
docker volume ls --format '{{.Name}}' | awk '/payment|pga|postgre/ {print $1}' | xargs -r docker volume rm

echo "Cleanup complete!"
