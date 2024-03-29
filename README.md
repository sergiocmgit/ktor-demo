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
5. Call the endpoints
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

## Documentation

Check the documentation when the application is running by accessing the following:

- Swagger documentation: [Swagger UI](http://localhost:8080/swagger-ui/index.html#/)
- OpenAPI documentation in json format: [OpenAPI in JSON](http://localhost:8080/v3/api-docs)
- OpenAPI documentation in yaml format: [OpenAPI in YAML](http://localhost:8080/v3/api-docs.yaml)
