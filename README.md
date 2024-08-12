# Voltmasters: Authorization Server
[![Authorization CI](https://github.com/voltmasters/authorization/actions/workflows/authorization-ci.yml/badge.svg)](https://github.com/voltmasters/authorization/actions/workflows/authorization-ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=voltmasters_authorization&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=voltmasters_authorization)

<!-- Image from etc -->
![Voltmasters](etc/charging.png)

## Description
This is the authorization server for the Voltmasters project.
It is responsible for managing the authentication and authorization of users.
It is built using the Spring Boot framework.
Uses Apache Kafka for messaging.

## Built With
- Spring Boot
- Apache Kafka

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites for Running
- Docker Compose

Check if Docker Compose is installed by running this command in the terminal or command prompt
```shell
docker compose version
```

Now just run this command to start the application inside the project directory
```shell
docker compose -f kafka-cluster.yml -f docker-compose.yml up -d
```

To stop the application, run this command in the project directory
```shell
docker compose down
```

### Prerequisites for Development
- Java 21
- Maven 3
- Docker
- IDE (IntelliJ IDEA, Eclipse, etc.)
- Git

### Running the Application
1. Clone the repository using Git in the terminal 
```shell
git clone git@github.com:voltmasters/authorization.git
```
2. Open the directory in the terminal
```shell
cd authorization
```
3. Run the application using Docker Compose
```shell
docker compose -f kafka-cluster.yml -f docker-compose.yml up -d
```

#### Kafka Cluster
- The kafka Cluster will be available at `localhost:9092,localhost:9093,localhost:9094`
- The docker internal network will be available at `kafka1:29092,kafka2:29093,kafka3:29094`
- You can also visualize the kafka cluster using [kafka-ui](https://docs.kafka-ui.provectus.io/) at [localhost:8080](http://localhost:8080/)

### Running the Tests
1. Run the tests using Maven in the project directory
```shell
mvn test
```

### Accessing the Application
- As the application is listening to the topic `charge-authorization-request`, you need to send valid requests to the topic.
- You can use Postman or Insomnia to send requests to the topic.
- The application will listen to the topic and respond to the requests.
- The application will send the response to the topic `charge-authorization-response`.
- You can check the logs to see the responses.

#### Example Request
```json
{
  "requestId": "3b0cf00c-137b-409e-899a-fb09c231fea4",
  "authorizationRequest": {
    "stationUuid": "257a19ed-4bff-4e32-b692-7da2e3860ef3",
    "driverIdentifier": {
      "id": "257a19ed-4bff-4e32-b692-7da2e3860ef3"
    }
  }
}
```

##### Parameters Description
- `requestId`: `UUIDv4` as `string` The unique identifier for the request.
- `authorizationRequest`: `JSON Object` The object containing the authorization request.
  - `stationUuid`: `UUIDv4` as `string` The unique identifier for the station.
  - `driverIdentifier`: `JSON Object` The object containing the driver's identifier.
    - `id`: `string` The unique identifier for the driver.
      - the driver identifier needs to be between 20 and 80 characters long.

#### Example Response
```json
{
  "authorizationStatus": "Accepted"
}
```

##### Parameters Description
- `authorizationStatus`: `string` The status of the authorization request.
  - There are **four** possible values:
    - `Accepted`: identifier is known and a flag is set which says the card is allowed to charge.
    - `Rejected`: identifier is known but card is not allowed for charging.
    - `Unknown`: identifier is not known.
    - `Invalid`: identifier is not valid.

### Improvments
- [ ] Implement distributed tracing
- [ ] Add more tests and increase test coverage
- [ ] Add more documentation to help understand the internal workings of the application
- [ ] Add robust error handling and logging
- [ ] Add security features
- [ ] *Please suggest more improvements to [contact@subhrodip.com](mailto:contact@subhrodip.com)
     or simple raise an [issue](https://github.com/voltmasters/authorization/issues/new/choose)*
