#! /bin/bash -e

docker run -p 8080:8080 --rm --name petstore petstore
#docker run -d -p 8080:8080 petstore