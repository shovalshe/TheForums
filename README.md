# forums-project

## Requirements
The following software must be installed before you are able to run the Forums project:
1. Java 11 
2. Maven
3. Node and npm
4. mysql 5.7+ installed and a forums schema created

Each of these components can be downloaded and installed at their respective web sites. Installations instructions vary by OS.

**_NOTE:_** An important note - By default the backend web server listens on port 8080. This is spring boot's default. Also by default we assume mysql is running on localhost on the default port of 3306, has a schema called 'forums' and a user 'root' with password 'admin1234' that has full read/write access to this schema (you can use `backend/forums-app-server/docker-compose.yml` if you use docker). To run with a different configuration, create a new properties files based on the project's application.properties and override the respective values. Then run the application with -Dspring.config.additional-location=[new properties file location]

Unzip the file 'TheForums.zip' to a directory of your choice (hence [directory]).

## To build and run the server code open a terminal and run the following commands:
```
cd [directory]
cd backend/forums-app-server

### build
mvn clean package

### run
cd target
java -jar forums-app-server-0.0.1-SNAPSHOT.jar 
```


## To build and run the front end code open a new terminal and run the following commands:
```
cd [directory]
cd forums-fe
npm install

### to run in development mode
npm start
```
