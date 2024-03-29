# ktor-demo

// TODO: fill this README

## Prerequisites

- Java 17
- Gradle 8.5
- Docker

## Installation

1. Clone the repository
   ```sh
   git clone git@github.com:sergiocmgit/ktor-demo.git
   ```
2. Build the image
   ```sh
   gradle build
   ```
3. Build the docker image
   ```sh
   gradle buildImage
   ```
4. Run the application
   ```sh
   gradle runDocker
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
