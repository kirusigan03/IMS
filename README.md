# Inventory Management System

A full-stack inventory management application built with Spring Boot for the backend and React + Vite for the frontend.

## Tech Stack

- Backend: Java 25, Spring Boot 4, Spring Security, JPA, MySQL
- Frontend: React 19, Vite, React Router, Axios, Recharts
- Authentication: JWT-based authentication
- File upload support for product images

## Project Structure

- `inventoryMgtSystem/` — Spring Boot backend
- `Frontend/` — React + Vite frontend
- `product-images/` — uploaded product images

## Features

- User registration and login
- Role-based access control
- Supplier and category management
- Product inventory management
- Purchase and sell operations
- Transaction tracking and history
- Dashboard analytics
- User profile management

## Prerequisites

- Java 25+
- Maven
- Node.js 18+
- MySQL database

## Backend Setup

1. Open the backend folder:
   ```bash
   cd inventoryMgtSystem
   ```
2. Update the database connection in `src/main/resources/application.properties`.
3. Run the server:
   ```bash
   ./mvnw spring-boot:run
   ```

The backend runs on:
- http://localhost:5050

## Frontend Setup

1. Open the frontend folder:
   ```bash
   cd Frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the app:
   ```bash
   npm run dev
   ```

The frontend runs on:
- http://localhost:3000

## Build

Backend:
```bash
cd inventoryMgtSystem
./mvnw clean package
```

Frontend:
```bash
cd Frontend
npm run build
```

## Environment Variables

Create a `.env` file in the frontend if needed for local environment configuration.

Example:
```env
VITE_API_BASE_URL=http://localhost:5050
```

## Notes

- Uploaded images are stored in the `product-images/` folder.
- The app uses MySQL and expects the database to already exist.
- The backend is configured with JWT secret values in `application.properties`.

## License

This project is for educational and internal use unless a different license is specified.
