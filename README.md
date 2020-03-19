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
   * If Mongo is not running in the default location, configure DID Mapping with the correct Mongo information. Create an `application.properties`, copy and replace the following variables in `<>` with your own values.
      ````bash
     spring.data.mongodb.host=<mongo-host>
     spring.data.mongodb.port=<mongo-host-port>
     spring.data.mongodb.database=did_mapping
      ````
* Run tests, build jar, and run DID Mapping using:
    ````bash
  mvn
  java -jar target/did-mapping-1.0-SNAPSHOT.jar 
  ````
* Regenerate swagger json after modifications to the REST API or models
    ````bash
  mvn -Pwrite-swagger-json
    ````
  
## Documentation
By default, once the service is running, documentation and example API calls can be found at:
* http://localhost:8080/swagger-ui.html#/did-map-controller
