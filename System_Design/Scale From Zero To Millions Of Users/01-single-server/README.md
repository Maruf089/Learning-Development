# Single Server Application

A scalable Spring Boot application with MySQL running on a single server. All components (web server, application server, and database) live on one machine.

## Architecture

- **Web Server**: Spring Boot embedded Tomcat (port 8080)
- **Application Server**: Spring Boot application with REST API
- **Data Storage**: MySQL database (port 3306)
- **All on one machine**: Docker containerization

## Prerequisites

- Docker
- Docker Compose

## Project Structure

```
01-single-server/
├── Dockerfile                 # Multi-stage Docker build
├── docker-compose.yml         # Orchestrates Spring Boot + MySQL
├── pom.xml                    # Maven dependencies
└── src/
    └── main/
        ├── java/com/scalable/singleserver/
        │   ├── SingleServerApplication.java
        │   ├── controller/
        │   │   └── UserController.java
        │   ├── service/
        │   │   └── UserService.java
        │   ├── repository/
        │   │   └── UserRepository.java
        │   └── entity/
        │       └── User.java
        └── resources/
            └── application.yml
```

## Getting Started

### 1. Build and Run with Docker Compose

```bash
docker-compose up --build
```

This command will:
- Build the Spring Boot application
- Start MySQL database
- Start Spring Boot application
- Create and link both containers on a shared network

### 2. Access the Application

Application is available at: `http://localhost:8080/api`

## API Endpoints

### Create User
```bash
POST /api/users?email=user@example.com&username=john&password=pass123
```

### Get All Users
```bash
GET /api/users
```

### Get User by ID
```bash
GET /api/users/{id}
```

### Update User
```bash
PUT /api/users/{id}?email=newemail@example.com&username=newname
```

### Delete User
```bash
DELETE /api/users/{id}
```

## Database Configuration

- **Host**: mysql (within Docker network)
- **Port**: 3306
- **Database**: myapp
- **User**: appuser
- **Password**: apppassword

## Limitations

This single-server approach is suitable for:
- Small to medium applications
- Initial development and testing
- Low to medium traffic scenarios

## Scaling Considerations

For scaling beyond a single server, consider:
- Load balancing
- Database replication
- Caching layers (Redis)
- Microservices architecture

## Cleanup

To stop and remove containers:

```bash
docker-compose down
```

To remove volumes as well:

```bash
docker-compose down -v
```
