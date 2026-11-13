# IN3101 - Enterprise Application Development
## Lab Sheet 03: Containerising the Library API with Docker

**Student index number:** 234109F

This repository contains the Library REST API supplied for Lab Sheet 02, with Docker configuration added for Lab Sheet 03.

## Project structure

```text
src/main/java/.../library_api/
  controller/BookController.java
  model/Book.java
  repository/BookRepository.java
  service/BookService.java
src/main/resources/application.properties
Dockerfile
compose.yaml
postman/IN3101-Lab03-Library-API.postman_collection.json
```

The local Spring Boot configuration uses port 8085 and MySQL at `localhost:3306`.
Compose overrides only the container configuration to use port 8080 and the
Compose service name `db`; the Java source and local development configuration
remain unchanged.

## Project technologies
- Java 21 and Spring Boot
- Spring Data JPA and MySQL
- Docker and Docker Compose
- Springdoc OpenAPI / Swagger UI

## Docker files
| File | Purpose |
|---|---|
| `Dockerfile` | Builds a small runtime image for the Spring Boot executable JAR |
| `.dockerignore` | Excludes source and development files from the Docker build context |
| `compose.yaml` | Starts MySQL and the Library API on a shared Compose network |
| `.env.example` | Documents the environment variable needed to run the database |
| `postman/IN3101-Lab03-Library-API.postman_collection.json` | Sample CRUD requests, including the required index-number book |

## Part A - Check Docker
Install Java 21 and Docker Desktop, start Docker Desktop, and wait for the
Docker Engine to be running. In PowerShell:

```powershell
java -version
docker --version
docker compose version
docker run hello-world
```

The final command should print `Hello from Docker`.

## Part B - Run MySQL in a container and test its volume

Start the MySQL container:

```powershell
docker run -d --name library-mysql `
  -e MYSQL_ROOT_PASSWORD=root123 `
  -e MYSQL_DATABASE=library_db `
  -p 3307:3306 `
  -v library-data:/var/lib/mysql `
  mysql:8.4
```

Check it is running:

```powershell
docker ps
```

Create a test row inside MySQL:

```powershell
docker exec -it library-mysql mysql -u root -p
```

Enter `root123`, then execute:

```sql
USE library_db;
CREATE TABLE note (msg VARCHAR(50));
INSERT INTO note VALUES ('stored in a volume');
exit
```

Remove the container and recreate it with the same volume:

```powershell
docker rm -f library-mysql
docker volume ls
docker run -d --name library-mysql `
  -e MYSQL_ROOT_PASSWORD=root123 `
  -e MYSQL_DATABASE=library_db `
  -p 3307:3306 `
  -v library-data:/var/lib/mysql `
  mysql:8.4
```

After MySQL starts, verify persistence:

```powershell
docker exec -it library-mysql mysql -u root -p
```

Then run:

```sql
SELECT * FROM library_db.note;
```

The row `stored in a volume` should still exist. Finally:

```sql
exit
```

```powershell
docker rm -f library-mysql
```

## Part C - Build the Library API Docker image

### 1. Build the JAR

From the project root, on Windows PowerShell:

```powershell
.\mvnw.cmd clean package -DskipTests
```

The build should finish with `BUILD SUCCESS` and create:

`target/library-api-0.0.1-SNAPSHOT.jar`

### 2. Build the Docker image

```powershell
docker build -t library-api .
docker images
```

### 3. Understand the expected database connection error

The Lab 02 `application.properties` uses `localhost:3306`. Inside a container, `localhost` refers to that same container, not the laptop or another container. Therefore, running the API container alone is expected to fail to connect to MySQL.

```powershell
docker run --name api-test -e SERVER_PORT=8080 -p 8080:8080 library-api
```

The `SERVER_PORT` override is needed because the Lab 02 local configuration
uses port 8085. The connection still fails because `localhost` inside this
container means the API container itself, where no MySQL server is running.
Stop it with Ctrl+C and remove it:

```powershell
docker rm api-test
```

## Part D - Run the complete system with Docker Compose

### 1. Configure the local password

Create a local file named `.env` in the project root:

```env
DB_ROOT_PASSWORD=root123
```

The real `.env` is ignored by Git and must not be committed. `.env.example` is safe to commit.

### 2. Start both services

Ensure port 8080 and 3307 are free. Then run:

```powershell
docker compose up --build -d
docker compose ps
docker compose logs api
```

Wait until the `db` service is healthy and `api` is running. The API log should show `Started LibraryApiApplication`.

The Compose configuration overrides the application's original local port
(8085) to 8080 inside the container and uses `jdbc:mysql://db:3306/library_db`
as the MySQL URL. No Java source code or `application.properties` change is
required.

### 3. Test the API

Use Postman with:

`http://localhost:8080/api/books`

Import the collection in `postman/IN3101-Lab03-Library-API.postman_collection.json` and run the requests. Create at least three books. One sample book title contains the student index number: `234109F - Clean Code`.

The sample collection includes POST, GET-all, GET-by-ID, PUT and DELETE requests. Run the create requests and GET-all request first. If testing update/delete, change the sample ID to an ID that actually exists.

The API endpoints are:

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/books` | Create a book |
| GET | `/api/books` | List all books |
| GET | `/api/books/{id}` | Get one book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book |

Swagger UI is available at:

`http://localhost:8080/swagger-ui/index.html`

### 4. Verify MySQL data

Connect MySQL Workbench using:
- Hostname: `127.0.0.1`
- Port: `3307`
- Username: `root`
- Password: the local `.env` value

Run:

```sql
SELECT * FROM library_db.book;
```

The books created through the API should appear. The `book` table is generated
from the inspected `Book` entity by Hibernate.

### 5. Verify that books survive container recreation

First, send `GET http://localhost:8080/api/books` and record the returned books and IDs.

Then run:

```powershell
docker compose down
docker compose ps
docker volume ls
docker compose up -d
docker compose ps
```

Wait for the database health check to pass. Send the GET request again:

`GET http://localhost:8080/api/books`

The same books and IDs should be returned because `docker compose down` removes containers and the network but keeps the named volume.

**Warning:** `docker compose down -v` deletes the volume and database data. Do not use it for the persistence test.

### 6. Stop the system

```powershell
docker compose down
```

## Important Docker commands

```powershell
docker image ls library-api
docker volume ls
docker compose ps
docker compose logs api
docker compose down
```

Do not use `docker compose down -v` during the persistence test because it
deletes the `library-data` volume and its records.

## GitHub and LMS submission checklist

- [ ] Docker Desktop is running and `docker run hello-world` succeeds.
- [ ] MySQL container and volume persistence test in Part B completed.
- [ ] Maven build succeeds and creates the JAR.
- [ ] `library-api` Docker image builds successfully.
- [ ] Docker Compose starts both services; MySQL is healthy.
- [ ] API CRUD requests work at `http://localhost:8080/api/books`.
- [ ] At least three books are created, including a title containing `234109F`.
- [ ] MySQL Workbench shows the books on port 3307.
- [ ] Books remain after `docker compose down` followed by `docker compose up -d`.
- [ ] Repository contains `Dockerfile`, `.dockerignore`, `compose.yaml`, and `.env.example`.
- [ ] The real `.env` file is not committed.
- [ ] Changes are pushed to the same GitHub repository used for Lab Sheet 02.

Push the changes from the project root:

```powershell
git status
git add Dockerfile .dockerignore compose.yaml .env.example .gitignore README.md postman
git commit -m "Complete IN3101 Lab Sheet 03 Docker setup"
git push
```

Submit the GitHub repository URL in the Lab Sheet 03 submission text area on the course LMS.

**Repository URL:** `https://github.com/YOUR-USERNAME/YOUR-REPOSITORY`

Replace the example URL with the actual repository URL after pushing.
