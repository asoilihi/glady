## Required on machine
 - JDK 17 
 - apache maven
 - IDE (optional)

## Clone the project and compile

- git clone this project and checkout branch develop and pull
* I used java 17, and an embedded database H2, and beautiful swagger to try our two Services Rest
* In this project I use MapStruct To convert entities objects to dto vice versa.
* You have to compile the project after clone it to generate the GladyMapperImpl
	in this path: target/generated-sources/annotations/com/backend/as/glady/mappers/GladyMapperImpl.java
   PS: you can run ('mvn clean compile' to compile the project and generate the mapper or 'mvn package' to run all tests)

## Start this app

* After Compiling the project, I hope that you will have build successed during the first try :) 
* Import as maven project in your prefered IDE (eclipse, itelliJ ...) or run it with command line if you prefer ;) 'mvn spring-boot:run'
* Run the project as springbootapp (MainClass --> CladyApplication.java)

### Visualize the data and try the service rest

* In the resources, we have data.sql with the requests to populate the database
	(After fighting some issues I decide to create myself the walletuser table and auto-increment the id)
* After running the project you can visualize the database H2 here: http://localhost:8080/h2-console
* You can also try the differents service via this swagger (http://localhost:8080/swagger-ui/index.html)
* I'm impatient for interviewing and talking about any ameliorations of this code

Thanks for taking time to read me :) 


