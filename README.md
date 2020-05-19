## BTrip - Business trip planner "Android" application

<p align="center">
  <img width="450" height="220" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/index.png">
</p>

### Related Back-End server github project repository
https://github.com/GioXmen/BTripExBackend

### 1 Overview of entire project
### 1.1 Context diagram for the whole project
- The project consists of a Android front-end application, which is used for user input, interaction and displaying results. The second part is the back-end server, which handles requests for data storage and retrieval. The server stores and retrieves data from a MySQL database. Secondly, the server is also capable of retrieving statistics data about the "COVID-19" global pandemic, serializing it into a usable data structure and passing it on to the front-end application. Lastly, the server is used for PDF report generation from user input data.

<p align="center">
  <img width="900" height="450" src="https://raw.githubusercontent.com/GioXmen/BTripExBackend/develop/BackEndReadme/ContextDiag.png">
</p>

### 1.2 Basic data model for Users, Trips and Events
- The data model consists of a user, that carries a username and password. The user may have more than one trip and a trip consists of name, description, destination, start date, end date and a thumbnail image. A trip may have more than one event associated to it, and an event consists name, description, location, start date, start time, end date, event type, event total expense and multiple expense images.
<p align="center">
  <img width="900" height="550" src="https://raw.githubusercontent.com/GioXmen/BTripExBackend/develop/BackEndReadme/Database_full.jpg">
</p>

### 1.3 Top down view of the created system - Component diagram for android application
The BTrip android application consists of these main components:

- User Interface (UI)
  - Login: Handles login, server url user input, login data validation
  - Register: Handles register user input, register data validation
  - Main (Trip): Responsible for trip list visualization, trip selection, adding/editing trip data, accepting trip data input and validation, trip thumbnail selection.
  - Event: Responsible for retrieving selected trip ID and holding event timeline, statistics and report functionality
    - Home (event timeline): Handles event timeline visualization, event selection, adding/editing event data, accepting event data input and validation, event expense image selection. 
    - Statistics: Responsible for statistics data visualization
      - Charts: Holds available statistics data chart types, that are used for visualization.
    - Report: Handles event expense list visualization and selection for report generation request
- Data: Handles receiving, sending requests. Packing data into objects and networking config.

<p align="center">
  <img width="1000" height="900" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/komponentu%20diagramav2.jpg">
</p>

### 2. Android front end application images with functionality overview
### 2.1 Login/Register
<p align="center">
  <img width="1000" height="600" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/login.jpg">
</p>

### 2.2 Trip list, add/edit trip, select date and image
<p align="center">
  <img width="1000" height="370" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/trip.jpg">
</p>

### 2.3 Event timeline, add/edit event, select event type, select time, expense list + edit/view
<p align="center">
  <img width="1000" height="370" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/event.jpg">
</p>

### 2.4 COVID-19 Statistics visualization
<p align="center">
  <img width="1000" height="600" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/covid.jpg">
</p>


### 2.5.1 Expense list, expense selection for report generation
<p align="center">
  <img width="600" height="600" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/report.jpg">
</p>


### 2.5.2 Generated Expense report using JasperReports
<p align="center">
  <img width="400" height="600" src="https://raw.githubusercontent.com/GioXmen/BTrip_Planner/develop/FrontEndReadme/generatedReport.jpg">
</p>
