You can reach the documentation and JAR file at this drive: https://drive.google.com/drive/folders/16E37EnU3PANL4lI1JC5-mJg7UoD5eNLM?usp=sharing

### To run the application in your local machine:

## For frontend:

Open cmd console in the frontend folder and execute "npm install" command for the node_modules.
After the install, you can execute "npm start" command to launch the frontend application. It will run on the port 3000 (localhost:3000).

Requirements:
- Node.js v18.18.0


## For backend:

- JAR Link to download: https://drive.google.com/file/d/1NAo_Ik7rhHV6OyItnKWv4ssofr-9TuCB/view?usp=sharing
  
You can launch the backend application in cmd with this command: "java -jar backend-0.0.1-SNAPSHOT.jar". Make sure that the file
backend-0.0.1-SNAPSHOT.jar is at the same directory as the console.
The application will be launched at port 8080.

Requirements:
- Java 17.0.8


## For database:

I used PostgreSQL for database operations. You can find the necessary properties about connecting to the database
in the file *application.properties* at resources folder in backend. By default, I used this properties:


- spring.datasource.url=jdbc:postgresql://localhost:5432/case_study
- spring.datasource.username=postgres
- spring.datasource.password=postgres


If you use these, a database named case_study must be created at your local Postgres server. Make sure that your port is 
same (5432), and you can change your username and password with your credentials. If you want to edit the properties, you need to
generate the .jar file again. You can execute the command "mvn clean package" in cmd console (make sure the directory is backend folder).
The newly generated file will be inside the "target" folder.

Requirements:
- PostgreSQL 16.0
