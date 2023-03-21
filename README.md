# RPG Character Management REST Application

This is a sample REST application built using Spring MVC that serves as an admin panel for managing RPG game characters. The application stores character data in an H2 database and provides functionality for sorting characters by various attributes, modifying user data including banning users, deleting users, creating new characters, and filtering output by various parameters. All methods are covered with JUnit 4 tests.
![admin-pannel](https://ic.wampi.ru/2023/03/21/SNIMOK1.png)

## Built With
This application was built using the following technologies:
-	Spring MVC
-	H2 Database
-	JUnit 4
- Maven

## Usage
Once the application is running, you can access the admin panel by navigating to http://localhost:8080/ in your web browser.
The admin panel provides the following functionality:
-	Sorting characters by various attributes such as name, level, and date created
-	Modifying user data including banning users 
![Modifying user](https://im.wampi.ru/2023/03/21/SNIMOK-3.png)
-	Filtering output by various parameters such as name, level, and date created
![Filtering output](https://ic.wampi.ru/2023/03/21/filter.png)
- creating new characters
![Creating](https://ie.wampi.ru/2023/03/21/create.png)
- deleting users

## API
The RPG Admin Panel provides a REST API for managing characters. The API endpoints are:
-	GET /rest/players: Get a list of all players.
-	GET /rest/count: Get players count.
-	POST /rest/players: Create a new player.
-	GET /rest/players/{id}: Get a specific player by ID.
-	POST /rest/players/{id}: Update an existing player.
-	DELETE /rest/players/{id}: Delete player.



## Running Tests
All methods in this application are covered with JUnit 4 tests. To run the tests, simply run ````mvn test```` from the project directory.

## Acknowledgments
-	Special thanks to the Spring team for creating such a great framework!
-	Thanks to the developers of H2 Database and JUnit for providing their excellent tools.
