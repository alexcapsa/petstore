mvn -f ..\..\pom.xml clean package

del .\build\*

cp ..\..\target\petstore-1.0-SNAPSHOT.jar ..\build

docker build -t acapsa/petstore .