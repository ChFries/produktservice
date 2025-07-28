mvn clean package -DskipTests=true
docker build . --no-cache -t produktservice:latest