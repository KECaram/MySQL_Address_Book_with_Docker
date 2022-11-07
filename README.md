# advanced_java_assignment5

How to run:
Set up your local MySQL environment:
- have your localhost DB set to port 3306
- create database address_book
- create address table using provided sql code
- create user sam with blank password and give that user full access to the address_book DB

Now you should just be able to run the program and use the provided data files in the resources folder to add entries to the database.
To add from a file go to localhost:8080/uploader
To add by user field form go to localhost:8080/
To search go to localhost:8080/search

To run tests: make sure that spring has fully shut down and run mvn test from the root project folder

DOCKER INSTRUCTIONS
-pull the app image with the following commands:
    docker pull kecaram/spring-app
-First we need to set up our database container for our Spring Boot app to connect to
-Run the following command to create a mysql database and launch it in a docker container:
    docker run -d -p 3307:3306 --name spring-mysql -e MYSQL_ROOT_PASSWORD=yourpassword 
    -e MYSQL_DATABASE=address_book mysql
-Now we need to set up the database so that it is compatible with our app.
-log into mysql in your running container with the following command:
    docker exec -it spring-mysql bin/bash
-log into mysql and set up your DB with the following commands:
    mysql -u root -p
    Enter yourpassword when prompted
    use address_book;
    create table address
    (
        id int not null primary key,
        first_name         varchar(255) null,
        last_name          varchar(255) null,
        street_address     varchar(255) null,
        additional_address varchar(255) null,
        city_or_town       varchar(255) null,
        state              varchar(2)   null,
        zipcode            varchar(9)   null,
        email              varchar(255) null,
        telephone          varchar(16)  null
    );
-then exit out of your bash session
-create a docker network with the following command:
    docker network create spring-net
-connect your mysql container to your network with the command:
    sudo docker network connect spring-net spring-mysql
-Finally run the containerized Spring Boot app on the same network as the containerized mysql
db with this command:
    docker run -p 8080:8080 --name app --net spring-net -e MYSQL_HOST=spring-mysql -e MYSQL_USER=root
    -e MYSQL_PASSWORD=yourpassword -e MYSQL_PORT=3306 kecaram/sping-app

-You should now be able to successfully access all available endpoints and have full
functionality:
    localhost:8080/uploader
    localhost:8080/form
    localhost:8080/search

***NOTE To stop the running containers use the 'docker container stop containername' command. 
To restart the containers use the 'docker container restart containername'.
You need to run the spring-app container on the outward facing port 8080 to get correct
endpoint mappings.

