# TODO API
API for TODO CRUD
 
## Properties
```
MONGO_DATABASE = Mongo database name
MONGO_HOST = Url of mongo host
MONGO_PORT = Port for mongo conection
APPLICATION_PORT = Port in wich the application will be running
```

## Docker
```
docker build -t todo-api --build-arg database=zerowork --build-arg database_port=27017 --build-arg database_host=127.0.0.1 --build-arg application_port=50080 .
docker run -d --network="host" --name todo-api todo-api
```

## Postman test suite
Import file Zerowork.postman_collection.json on postman, or run by newman.

Environment variables
```
todo-api = url for service. for instance, http://localhost:50080
```