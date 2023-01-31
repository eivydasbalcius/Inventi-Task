# Inventi-Task
A service which sole purpose is to manage bank account balance via Rest API.

# Docker Desktop Setup

* Start a postgres instance:
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres

* Run "postgres" Image to create new Container.

* Run the newly created Container.

# Running project via IntellijIdea

* Clone project from git hub:
git clone https://github.com/eivydasbalcius/Inventi-Task.git

* Open project repository via IntelijIdea IDE

* Connect to PostgreSQL database

![image](https://user-images.githubusercontent.com/72504144/215752025-5e5ec13e-4006-47b8-a1af-5fd56cd147c2.png)

* Debub AppApplication.java file

# Postman usage

* http://localhost:8080/api/balance

![image](https://user-images.githubusercontent.com/72504144/215752173-4de5942b-6231-4210-b475-ec06ad0002d4.png)

* http://localhost:8080/api/upload

![image](https://user-images.githubusercontent.com/72504144/215752222-fbf53671-7c6d-4e4b-938b-5e1d422570dc.png)

* http://localhost:8080/api/download

![image](https://user-images.githubusercontent.com/72504144/215752274-e2dacdbc-502b-4ae2-84b3-1e22e0412683.png)