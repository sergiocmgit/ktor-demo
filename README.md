# ktor-demo

This demonstration service exemplifies the application of **domain-driven
design and hexagonal architecture** in modeling diverse use cases for **scooter
rental businesses**. In this showcase, the **Ktor** framework has been employed
to facilitate comparison with the well-established Spring services.

## Prerequisites

- Java 17
- Docker

## Installation

1. Clone the repository
   ```sh
   git clone git@github.com:sergiocmgit/ktor-demo.git
   ```
2. Build the image
   ```sh
   ./gradlew build
   ```
3. Build the docker image
   ```sh
   ./gradlew buildImage
   ```
4. Run the application
   ```sh
   ./gradlew runDocker
   ```

## Test the endpoints

 ```sh
curl -X GET "http://localhost:8080/users"
```
 ```sh
curl -X GET "http://localhost:8080/scooters"
```
 ```sh
curl -X POST "http://localhost:8080/scooters/2/lock/A"
```
 ```sh
curl -X POST "http://localhost:8080/scooters/3/run/A"
```
 ```sh
curl -X POST "http://localhost:8080/scooters/1/run/B"
```
 ```sh
curl -X POST "http://localhost:8080/scooters/1/run/C"
```
 ```sh
curl -X POST "http://localhost:8080/scooters/4/run/A"
```
 ```sh
curl -X POST "http://localhost:8080/scooters/3/run/A"
```
