# kn-backend

Back-End: The BackEnd was developed in Java 11 using Spring Boot 2 (v2.5.6), developed in Spring 5 (very good support), taking advantage of the optimization of tasks automation resources. Here I use other technologies such as spring-data, spring-web, lombok, spring-boot-maven-plugin, etc... I didn't have the need for others that could be added too.

For unit tests, JUnit 4.13.2 was used with spring-test and MockMvc to mock the data.

To run the backend it has to be run like this:

$ mvn spring-boot:run spring-boot.run.jvmArguments=-noverify -XX:TieredStopAtLevel=1 spring-boot.run.mainClass=com.example.BasicApplication Env.SPRING_OUTPUT_ANSI_ENABLED=always

or also:

$ mvn clean package spring-boot:repackage $ java -jar target/basic-0.0.1-SNAPSHOT

* More arguments can be added (memory, cache, etc...) also to improve performance, depending on the infrastructural context where it is executed.

* After deploy file people.csv is processed to populate de DB.


## *Endpoints*

When executing these lines, it will listen on port 8080, which the client (frontend) will get its endpoints, describing the endpoints (I could have used swagger to document the services but as there are few, I thought it was not worth it):

http://localhost:8080/processFile
--> PARAMETERS: A MultipartFile named 'file'

--> RETURN: A boolean success/error

--> FUNCTION: Adds the contacts of the files in the DB

* ***The file that has to be loaded must follow the CSV standards (as people.csv) file to be well normalized/parsed.***
* ***Controls have minimum validations in the URL image*** 

http://localhost:8080/listContacts
--> PARAMETERS: --- name (if empty returns all records)

--> RETURN: A list of objects

--> FUNCTION: Lists contacts

http://localhost:8080/reset
--> PARAMETERS: --- 

--> RETURNS: ---

--> FUNCTION: Clear contact table

## *Database* 

MySQL Server 8.0.27-1debian10 was used (image hosted in Hub Docker), for record persistence, to download the image from hub-docker site 
(https://hub.docker.com/_/mysql).

Settings are described in application.properties (src/main/resources/application.properties).

Once the docker image (or any platform SQL running) is up and mysql service is running, DML needs to be created, execute the commands described below:

> mysql> create database knproject;

> mysql> use knproject;

> mysql> create table test(name VARCHAR(20) PRIMARY KEY,url VARCHAR(200) NOT NULL);


