# Contract Management

Contract Management is a web-based application for managing contracts efficiently. It enables users to organize, track, and maintain contracts in a structured way, ensuring that important deadlines and documents are never overlooked.

## Features

- Create, edit, and delete contracts  
- Manage contract durations and notice periods  
- Upload and view contract documents  
- Automatic reminders for expiring contracts  
- Categorization and filtering options  
- User-friendly interface built with Vaadin

## Tech Stack

- **Framework**: [Vaadin](https://vaadin.com/)
- **Backend**: Java (Spring Boot or plain Java, depending on your setup)  
- **Database**: MariaDB
- **Build Tool**: Maven
- **Deployment**: Can be run as a standalone JAR or deployed to a servlet container


## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different
IDEs](https://vaadin.com/docs/latest/flow/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/contractmanagement-1.0-SNAPSHOT.jar`

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). This setup uses
  [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.

## Deploying using Docker

To build the Dockerized version of the project, run

```
docker build . -t contractmanagement:latest
```

Once the Docker image is correctly built, you can test it locally using

```
docker run -p 8080:8080 contractmanagement:latest
```
