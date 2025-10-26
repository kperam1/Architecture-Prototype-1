
This repo benchmarks MongoDB vs MySQL for inserting and fetching 1000 user stories.
Prerequisites
Java 11+
Maven
MySQL installed and running
MongoDB installed and running
Setup
1. Clone and build
git clone https://github.com/kperam1/Architecture-Prototype-1.git
cd Architecture-Prototype-1
mvn clean install

2. Database setup
MySQL
Start your MySQL server.
Create a database named agile_re:
CREATE DATABASE agile_re;


Create the user_story table inside agile_re:
USE agile_re;
CREATE TABLE user_story (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    priority VARCHAR(50)
);
MongoDB
Start your MongoDB server (default: mongodb://localhost:27017).
Database agile_re and collection user_story will be created automatically when running the program. No manual setup required.
Running the Benchmarks
Run MySQL Test
mvn exec:java -Dexec.mainClass="com.prototype.MySQLTest"

Output:
Connects to MySQL.
Clears table user_story.
Inserts 1000 stories.
Fetches all stories.
Prints insert and fetch timings.
Run MongoDB Test
mvn exec:java -Dexec.mainClass="com.prototype.MongoTest"

