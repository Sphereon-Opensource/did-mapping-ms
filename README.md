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
* Regenerate swagger json after modifications to the REST API or models. (This is also done by default in the generate-test-resources phase.)
    ````bash
  mvn -Pwrite-swagger-json
    ````
  
## Docker & Kubernetes support
#####Environment settings
Before Docker or Kubernetes profiles can be executed, you'll need some environment settings.
The registry settings in the pom.xml can be overridden on the mvn command.
````
-Ddocker.registry.push=<hostname for container push registry>
-Ddocker.registry.pull=<hostname for container pull registry> (The default is docker.io)
````
When using Kubernetes you can us an existing secret to access the container registry (This will be used in the deployment descriptor)  
````
-Ddocker.registry.push.secret-name=<secret name to access the conatiner registry>
````
When using Kubernetes, the mongo database settings can be overridden with:
````
-Dconfig.mongodb.hostname=mongo
-Dconfig.mongodb.port=27017
-Dconfig.mongodb.database=did_mapping
````
The server protocol, tcp ports and exposed ports can be overridden.
The default is
````
-Dcontainer.enable-tomcat-ajp=false
-Dtcp.port.internal=8080
-Dtcp.port.exposed=8801
````
To switch to AJP mode
````
-Dcontainer.enable-tomcat-ajp=true
-Dtcp.port.internal=8009
-Dtcp.port.exposed=127.0.0.1:8096
````
   
##### Build profiles
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
