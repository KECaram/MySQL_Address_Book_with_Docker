# advanced_java_assignment5

How to run:
Set up your local MySQL environment:
- have your localhost DB set to port 3306
- create database address_book
- create address table using provided sql code
- create user sam with plank password and give that user full access to the address_book DB

Now you should just be able to run the program and use the provided data files in the resources folder to add entries to the database.
To add from a file go to localhost:8080/uploader
To add by user field form go to localhost:8080/form
***NOTE - when pressing submit on user field form, an entry will be added to the db even though you are not rerouted to a new page
so make sure to query DB to confirm add

To run tests: make sure that spring has fully shut down and run mvn test from the root project folder
