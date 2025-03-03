<div align="center">
   <h1>Cabin Crew Scheduling System</h1>
</div>

## About the project

<p style="font-size:18px;">
This is a Java and Spring Boot application I developed to simplify and streamline the monthly scheduling process for cabin crew members, including myself. Recognizing the need for a more efficient and faster way to manage flight requests and days off, I meticulously analyzed and designed this tool. The application takes into account crucial factors like flight timings, mandatory rest periods, flight time limitations, and aircraft certifications, making the scheduling process both faster and more effective. After nearly 1.5 years of dedicated work, evolving from a console-based tool to a robust Spring Boot REST API, the project now includes 80 classes, more than 3,400 lines of code, and comprehensive tests. This complete and reviewed solution meets my initial needs, and I’m eager to continue expanding its capabilities.
</p>

## Table of Contents
1. [About The Project](#about-the-project)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Roadmap](#roadmap)

<!-- Installation section -->
<details>
  <summary><h2 style="margin-top: 10px;">Installation</h2></summary>

### Prerequisites

Make sure you have the following software installed:

- **Java 17** or newer: [Download Java](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- **Maven**: [Download Maven](https://maven.apache.org/download.cgi)
- **Git**: [Download Git](https://git-scm.com/downloads)

### Installation Steps

1. **Clone the repository:**

   ```bash
   git clone https://github.com/PrzemyslawGromiec/Crew-Bidding-Spring.git
   ```

2. **Navigate to the project directory:**

   ```bash
   cd Crew-Bidding-Spring
   ```

3. **Build the project using Maven:**

   Ensure that Maven is installed and properly set up on your system. Then run:

   ```bash
   mvn clean install
   ```

4. **Run the application:**

   You can run the application directly using Maven or by running the generated JAR file:

   **Option 1: Using Maven**

   ```bash
   mvn spring-boot:run
   ```

   **Option 2: Running the JAR file**

   After building the project, a JAR file will be generated in the `target` directory. Run it with:

   ```bash
   java -jar target/Crew-Bidding-Spring-0.0.1-SNAPSHOT.jar
   ```

5. **Access the application:**

   Once the application is running, you can access it by navigating to:

   ```
   http://localhost:8080
   ```

</details>

<!-- todo --
0. deploy app 
1. opisac endpointy razem ze sciezka
2. skrocic opis
3. opisac jak stworzyc raport od poczatku
4. ew opcjonalne endpointy 
5. parsowanie lotow z pliku i generowanie ich na caly miesiac
6. frontend wyzej
7. przemyslec w ogole feedback 
8. screen do swagger w Usage - link do swaggera -->
<!-- Usage section -->
<details>
  <summary><h2 style="margin-top: 10px;">Usage</h2></summary>

This application is designed to generate a comprehensive report for the upcoming month, allowing cabin crew members to plan their schedules efficiently. Users can request specific days off by submitting their preferences through the application.

### How It Works

1. **Submitting Day Off Requests:**
   - Users can submit their day off requests via the application. These requests are processed through a controller that accepts a DTO (Data Transfer Object). The DTO allows users to specify the start date, end date, priority, and a description for each day off request.
   - The submitted data is then stored in an H2 database for further processing.

2. **Available Flights Data:**
   - The H2 database also stores information about all available flights for the entire month. This data includes details such as flight duration and aircraft type.

3. **Generating the Report:**
   - Based on the day off requests, the application generates periods that adhere to aviation regulations regarding mandatory rest times for cabin crew members.
   - Within these periods, users can assign available flights that fit their schedule.

4. **Filtering and Assigning Flights:**
   - Users can filter available flights based on their duration and the type of aircraft.
   - Once the flights have been selected, they can be assigned to the appropriate periods between rest times.

5. **Finalizing the Report:**
   - After all flights have been assigned, users can finalize the report by using the appropriate endpoint.
   - The final report will include both the requested days off and the selected flights, organized according to their priority.

This process ensures that cabin crew members can effectively manage their schedules while complying with aviation regulations, making the scheduling process both streamlined and compliant.

### Example Usage

1. **Requesting Days Off:**
   - Submit a request with the desired start date, end date, priority level, and a brief description of the reason for the request.

2. **Filtering Flights:**
   - Filter available flights by duration or aircraft type to find the most suitable options for your schedule.

3. **Assigning Flights:**
   - Assign flights to the generated periods between rest times to build out your schedule for the month.

4. **Finalizing the Schedule:**
   - Once all flights are assigned, finalize the report to complete your schedule. The final report will display your day off requests and the assigned flights, prioritized accordingly.
   
</details>

<!-- Usage section -->
<details>
  <summary><h2 style="margin-top: 10px;">Roadmap</h2></summary>

Here’s a look at the future development plans for the Cabin Crew Scheduling System:

### Upcoming Features

- **Employer Decision Integration**:
   - Develop a system where employers can input dates for scheduled training sessions for employees, ensuring that training periods are taken into account during schedule generation.

- **Alternative Planning Modes**:
   - Expand the application to accommodate alternative scheduling needs, such as those for flight crew or other types of users, ensuring that the system is flexible and meets the diverse requirements of different user groups.

- **User Feedback System**:
   - Implement a feature that allows users to provide feedback on the application directly. This feedback will be used to make continuous improvements and prioritize new features.

- **Frontend Development**:
   - Create a user-friendly frontend for the application, enabling it to function as a web-based platform. This will allow users to access the system through a web browser, improving accessibility and ease of use.

### Contributing to the Roadmap

If you have ideas or features you'd like to see in future versions, feel free to open an issue or submit a pull request. We welcome contributions that help make this project better for everyone!
</details>

<p align="left"> 
   <img src="https://komarev.com/ghpvc/?username=pgromiec&label=Profile%20views&color=0e75b6&style=flat" alt="pgromiec" /> 
</p>

- 🔭 I’m currently working on [Airline Crew Scheduling App](https://github.com/PrzemyslawGromiec/Crew-Bidding-Spring)
- 👨‍💻 All of my projects are available
  at [https://github.com/PrzemyslawGromiec?tab=repositories](https://github.com/PrzemyslawGromiec?tab=repositories)
- 📫 How to reach me **dev.gromiec@gmail.com**

<h3 align="left">Connect with me:</h3>
<p align="left">
   <a href="https://www.linkedin.com/in/pgromiec/" target="blank">
      <img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="przemyslaw gromiec" height="30" width="40" />
   </a>
</p>

<h3 align="left">Languages and Tools:</h3>
<p align="left"> 
   <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> 
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> 
   </a> 
   <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> 
      <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> 
   </a> 
   <a href="https://www.java.com" target="_blank" rel="noreferrer"> 
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> 
   </a> 
   <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> 
      <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> 
   </a> 
   <a href="https://spring.io/" target="_blank" rel="noreferrer"> 
      <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> 
   </a> 
</p>

