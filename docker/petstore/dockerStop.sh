#! /bin/bash -e


docker stop petstore

docker rm $(docker ps -a -q)