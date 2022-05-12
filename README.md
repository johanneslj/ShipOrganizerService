# Ship Organizer Service for

This project is the result of a bachelor thesis at NTNU Ålesund. <br>
The Ship Organizer Service is the backend system for the Sea Storage application developed for Giske Kystfiske.

## Deployment

The Spring Boot application is meant to run using the included docker compose file. <br>
You can run the project locally by running the docker compose file.
This creates the server and MySQL database. 

The server runs on port 6868
## Requirements
Computer with functionality to run a Docker server.<br>
Docker can be on Windows, macOS or Linux

Installment of docker found here: https://docs.docker.com/get-docker/

## Technologies
<ul>
<li>Docker</li>
<li>Spring Boot</li>
<li>Maven</li>
<li>Spring Security</li>
<li>MySQL</li>
</ul>

## Src folder

```
├── src
│   ├── Auth <-- Jwt Authentication filter
│   ├── Config <-- Configuration files
│   ├── Controller <-- Contains all controller classes 
│   ├── Model <-- Contains all entity classes
│   ├── Repository <-- Contains all repository interfaces
│   ├── Service <-- Contains all service classes
│   ├── UserPrincipal <-- Contains different user principals
│   └── ShipOrganizerServiceApplication.java <-- main class for project
```
## Authors
<ul>
<li>Hans Andreas Lindgård</li>
<li>Simon Duggal</li>
<li>Johannes Løvold Josefsen</li>
</ul>