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
  
## Docker & Kubernetes support
* To build a docker image
    ````bash
  mvn -Pf8-build
    ````
* To start a docker container
    ````bash
  mvn -Pdocker-start
    ````
* To push the image to a remote registry
    ````bash
  mvn -Pf8-push
    ````
* To apply the service to Kubernetes
    ````bash
  mvn -Pf8-apply
    ````
* Itś possible to combine
    ````bash
  mvn -Pf8-build -Pf8-push -Pf8-apply
    ````

## Documentation
By default, once the service is running, documentation and example API calls can be found at:
* http://localhost:8080/swagger-ui.html#/did-map-controller
