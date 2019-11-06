# DID Mapping Microservice
A microservice for storing associations between user IDs and DIDs for applications that want to use DID-Auth
## Building and Running
* Clone git repo
    ```bash
  git clone git@github.com:Sphereon/did-mapping-ms.git
  cd did-mapping-ms
  ```
* Setting up Mongo
    * First start Mongo and create a new DB for use by DID Mapping
      ```bash
      sudo service mongod start
      mongo
      > use did_mapping
      ```
   * Configure DID Mapping (`src/main/resources/application.properties`) with the correct Mongo information. The default is bellow:
      ````bash
     spring.data.mongodb.host=127.0.0.1
     spring.data.mongodb.port=27017
     spring.data.mongodb.database=did_mapping
      ````
* Run tests, build jar, and run DID Mapping using:
    ````bash
  mvn package
  java -jar target/did-mapping-1.0-SNAPSHOT.jar 
  ````
## Documentation
By default, once the service is running, documentation and example API calls can be found at:
* http://localhost:8080/swagger-ui.html#/did-map-controller
