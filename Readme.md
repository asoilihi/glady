## Clone the project

- git clone this project and checkout:
* I used java 17, an embedded database H2 and a beautiful swagger to try the two Service Rest
* In this project I use MapStruct To convert entities objects to dto vice versa.
* You have to compile the project after clone it to generate the GladyMapperImpl
	in this path: target/generated-sources/annotations/com/backend/as/glady/mappers/GladyMapperImpl.java
   PS: you can run ('mvn clean compile package' for example to run all tests)

## Run the 

* After Compiling the project, I hope that you will have build successed during the first try :) 
* Import as maven project in your prefered IDE 
* Run the project as springboot (MainClass --> CladyApplication.java)

### Visualize the data and try the service rest

* In the resources, we have data.sql with the requests to populate the database
* After running the project you can visualize the data in H2 here: http://localhost:8080/h2-console
* You can also try the differents service via this swagger (http://localhost:8080/swagger-ui/index.html)


