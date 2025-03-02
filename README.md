FarmCollector API Documentation
Overview
FarmCollector is a Spring Boot application designed to manage farms, crops, planting, and harvesting data. It provides APIs for managing planting records, harvesting data, and generating reports. Reports are viewable in a browser.

Technologies
Java 17+
Spring Boot
Spring Data JPA (with PostgreSQL)
Thymeleaf (for reports)

Endpoints
1. Planting APIs
1.1 Show Planting Form
 Displays a form to enter planting details.
 - URL: GET /api/planting/form
Response: Returns a Thymeleaf template (planting-form.html)
Parameters: None
1.2 Save Planting Data
 Saves planting data and creates a corresponding report entry.
 - URL: POST /api/planting/save
Request Parameters:
farmId (Long) - ID of the farm
cropId (Long) - ID of the crop
seasonId (Long) - ID of the season
areaInAcres (double) - Land area planted
expectedProduct (double) - Expected production
Response: Returns a Thymeleaf template upon successful creation(success.html).

2. Harvesting APIs
2.1 Show Harvesting Form
 Displays a form for entering harvesting data.
 - URL: GET /api/harvesting/form
Response: Returns a Thymeleaf template (harvesting-form.html)
2.2 Save Harvesting Data
 Saves actual harvested product and updates the corresponding report.
 - URL: POST /api/harvesting/save
Request Parameters:
actualProduct (double) - Actual harvested amount
plantingId (Long) - ID of the planting record
Response: Returns a Thymeleaf template upon successful creation(success.html).

3. Report APIs
3.1 View All Reports
 Fetches all reports for planting and harvesting.
 - URL: GET /api/reports
Response: Renders a Thymeleaf template (report.html) displaying the reports.

Setup Instructions
1. Prerequisites
Java 17+
PostgreSQL
Maven (mvn)

2. Configure Database
Update application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/farmcollector
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

3. Access the API
🌱 Planting Form: http://localhost:8080/api/planting/form
🌾 Harvesting Form: http://localhost:8080/api/harvesting/form
📊 View Reports: http://localhost:8080/api/reports


Unit Tests

FarmDataControllerTest
 /planting/save
 /harvesting/save
 
ReportViewControllerTest
 /api/reports

