# simple-cab

API for retrieving summaries of NY Cab Trip Data

## Design Notes

For this version of the simple-cab API, I opted to implement caching for both client and server. In terms of efficiency, it makes more sense for the server to manage its own cache as it could in theory serve multiple clients. The client also manages its own cache so as to avoid making unecessary requests to the server.

## Limitations

The API does not check whether requested medallions are valid or existing. In the case of an unexisting medallion id, the API will return 0.

The server cache is never invalidated while it's running. I assumed that the data is static for this exercise. Future improvements may include clearing or invalidating entries in the cache when new trip data is added.

## Build Dependencies

- Maven
- Spring Boot Web Starter
- MySQL Connector/J
- Jackson Databind
- Apache HttpClient Fluent API

## Building Instructions

```
maven clean package
```

## Running Instructions

### MySQL Database

For convenience, scripts to setup a preloaded MySQL docker container is provided. To set it up, run:

```
./bin/run-db.sh
```

WARNING: It will take about half-an-hour to load all the trip data into the database!

### Server

To run the server, execute:

```
./bin/run-server.sh
```

Note that the server expects the mysql database defined above to be up and running. If you are using a different database configuration, you need to modify `bin/application.properties` and restart the server.

### Client

To run the client, execute:

```
./bin/run-client.sh <command> <url> <date> <medallion>...
```

Client cli usage:

```
usage: Client <command> <url> <date> <medallion>...

<command>
  load          load cab trip counts of the specified date
  load-fresh    load cab trip counts and ignore the cache
  clear         clears the local cache
  help          prints out this help screen

<url>           url of the server

<date>          date in the format yyyy-mm-dd

<medallion>...  cab medallions to retrieve separated by spaces
```

For example,

```
./bin/run-client.sh load http://localhost:8080/ 2013-12-01 D7D598CD99978BD012A87A76A7C891B7 5455D5FF2BD94D10B304A15D4B7F2735
```

### REST API

Since the server provides a REST API, you can also access it via curl, like so:

```
curl "http://localhost:8080/report/trips/?medallion=D7D598CD99978BD012A87A76A7C891B7&medallion=5455D5FF2BD94D10B304A15D4B7F2735&date=2013-12-01"
```

And the reply will be in JSON format like so:

```
{
    "D7D598CD99978BD012A87A76A7C891B7": 3,
    "5455D5FF2BD94D10B304A15D4B7F2735": 2
}
```