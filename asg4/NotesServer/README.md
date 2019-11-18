# NoteServer

A server for the Notes app in CS616 (Fall 2019). The NoteServer software is provided with source
code and is built using gradle.

The current version uses an in-memory database (H2
[http://www.h2database.com/html/main.html]). Restarting the server will reset the database to it's
initial state.

# Operating environments (hardware, OS, platform)

The NoteServer will work on any platform that has a Java 1.7 (or 1.8) SE JDK. It has been tested on
Linux and Mac OS X.

# Software dependencies

For b software is needed to install and run the NoteServer, but only Java needs to be
installed manually. Gradle and spring.io will be installed automatically by the gradle wrapper.

*   Java SE JDK [http://www.oracle.com/technetwork/java/javase/downloads/index.html]
*   (optional) IntelliJ IDEA [https://www.jetbrains.com/idea/]
*   gradle [http://gradle.org/]
*   spring.io [http://spring.io/]

# Installation

## Configure spring.io Server

Update the server configuration file `src/main/resources/application.properties`. Currently, you can change the port the server runs on:

    server.port=9999

# Start the Server
 
Starting the server from the terminal is easy:

    $ ./gradlew bootRun

A lot happens with this command:

1.   The gradle wrapper downloads gradle.
2.   Gradle downloads spring.io and dependencies.
3.   The server code is compiled by the Java compiler, generating a `.jar` library.
4.   It runs the server using the settings in `application.properties`.

# (Optional) Use IntelliJ

The server is given to you as an IntelliJ project. The above steps can be accomplished in the IDE.

# Entity URLs

## Note repo

The following URLs can be used in the note repo:

    http://localhost:9999/note
    http://localhost:9999/note/e187ecea-167c-4feb-a13a-eb34949bb400
    http://localhost:9999/note/e187ecea-167c-4feb-a13a-eb34949bb400/collaborators
    
## User repo

The following URLs can be used in the note repo:

    http://localhost:9999/user
    http://localhost:9999/user/3faa2495-f0f1-4408-ae24-d482f37caf1c
    http://localhost:9999/user/3faa2495-f0f1-4408-ae24-d482f37caf1c/notes

## Collaborator repo

The following URLs can be used in the collaborator repo:

    http://localhost:9999/collaborator/1
    http://localhost:9999/collaborator/1/note
    http://localhost:9999/collaborator/1/user
