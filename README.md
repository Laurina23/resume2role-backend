# Resume2Role Backend — AI Mock Interview System

Spring Boot backend powering the Resume2Role AI interview platform.

This backend handles resume analysis, AI-powered interview generation, real-time interview session management, answer evaluation, authentication, and interview report generation using Gemini AI and MongoDB.

---

# Features

## Authentication & Security
- JWT-based authentication
- User registration and login
- Secure protected APIs
- Spring Security integration

## Resume Processing
- Resume upload support
- Resume text extraction
- Resume storage in MongoDB
- AI-driven technical profile analysis

## AI Interview Engine
- Gemini AI-powered interview question generation
- Resume-aware technical interviews
- Sequential interview management
- Dynamic interview session handling

## AI Answer Evaluation
- AI-generated:
  - score
  - feedback
  - improvement suggestions
- Per-question evaluation storage
- Intelligent interview analysis

## Interview Report System
- Overall interview score calculation
- Strength analysis
- Weakness analysis
- Final AI-generated verdict
- Performance summary generation

## Database Management
- MongoDB integration
- Repository-based architecture
- Persistent interview storage
- Resume and evaluation tracking

---

# Tech Stack

## Backend Framework
- Spring Boot
- Java 17

## Database
- MongoDB

## Security
- Spring Security
- JWT Authentication

## AI Integration
- Gemini AI API

## Build Tool
- Maven

---

# Backend Architecture

```text
Client Request
      ↓
Controller Layer
      ↓
Service Layer
      ↓
Gemini AI / MongoDB
      ↓
Business Logic Processing
      ↓
Response Generation
```

---

# Project Structure

```text
resume2role-backend/
│
├── src/main/java/com/resume2role/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── model/
│   ├── repository/
│   ├── security/
│   ├── service/
│   └── Resume2RoleApplication.java
│
├── src/main/resources/
│   └── application.properties
│
├── pom.xml
└── README.md
```

---

# Core Modules

## Authentication Module
Handles:
- user registration
- login
- JWT token generation
- request authorization

## Resume Module
Handles:
- resume upload
- resume storage
- extracted text processing
- profile analysis

## Interview Module
Handles:
- interview creation
- question generation
- answer submission
- interview progression

## Evaluation Module
Handles:
- AI answer evaluation
- score generation
- improvement analysis
- feedback processing

## Report Module
Handles:
- result aggregation
- interview analytics
- final performance report generation

---

# API Endpoints

## Authentication APIs

```http
POST /auth/register
POST /auth/login
```

---

## Resume APIs

```http
POST /resume/upload
GET /resume/{id}
```

---

## Interview APIs

```http
POST /interview/start
POST /interview/submit
GET /interview/{id}
GET /interview/result/{id}
```

---

# Interview Flow

```text
Resume Upload
      ↓
Resume Analysis
      ↓
Gemini AI Question Generation
      ↓
Interview Session Creation
      ↓
Frontend Sequential Question Rendering
      ↓
Answer Submission
      ↓
Gemini AI Evaluation
      ↓
Evaluation Storage
      ↓
Final Interview Report
```

---

# Setup Instructions

## Clone Repository

```bash
git clone <repository-url>
cd resume2role-backend
```

---

# Configure Environment Variables

Create:

```text
src/main/resources/application.properties
```

Add:

```properties
spring.data.mongodb.uri=YOUR_MONGODB_URI

jwt.secret=YOUR_JWT_SECRET

gemini.api.key=YOUR_GEMINI_API_KEY
```

---

# Run Backend Server

## Using Maven Wrapper

```bash
./mvnw spring-boot:run
```

---

## Or Using Maven

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

# Key Backend Services

## GeminiService
Handles:
- AI question generation
- AI answer evaluation
- Gemini API communication

## InterviewService
Handles:
- interview lifecycle
- question progression
- answer storage

## EvaluationService
Handles:
- evaluation parsing
- score extraction
- feedback generation

## ResultService
Handles:
- report generation
- score aggregation
- strengths and weaknesses analysis

---

# Database Collections

## Users
Stores:
- authentication details
- user profile data

## Resumes
Stores:
- uploaded resumes
- extracted resume text
- technical profile data

## Interviews
Stores:
- generated questions
- submitted answers
- AI evaluations
- interview reports

---

# Future Enhancements

- Multi-round interview support
- Coding interview APIs
- AI confidence analysis
- Interview history dashboard
- Exportable PDF reports
- Real-time analytics
- Admin monitoring panel

---

# Author

Laurina Patnaik

---

# License

This project is developed for academic and learning purposes.
