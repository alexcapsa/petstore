#! /bin/bash -e

mvn -f ../../pom.xml clean package

rm -fr build

mkdir build

cp ../../target/petstore-1.0-SNAPSHOT.jar build

#docker build -t petstore .
