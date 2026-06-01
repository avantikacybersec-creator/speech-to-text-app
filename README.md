Speech-to-Text (STT) Application

Project Overview

Speech-to-Text (STT) Application is a full-stack web application that allows users to:

* Register and log in securely
* Upload audio files
* Convert speech into text using Deepgram API
* Store transcription history in PostgreSQL
* Upload audio files to Cloudinary
* View previous transcripts
* Delete transcripts when required

⸻

Tech Stack

Backend

* Java 25
* Spring Boot
* Spring Data JPA
* Spring Security
* PostgreSQL
* Deepgram API
* Cloudinary
* Maven

Frontend

* React.js
* Axios
* Bootstrap / CSS
* REST API Integration

⸻

Features Implemented

User Management

User Registration

* Create new account
* Email validation
* Password encryption using BCrypt

User Login

* Login using email and password
* Password verification using BCrypt

⸻

Speech-to-Text Module

Audio Upload

Supported formats:

* MP3
* WAV
* M4A

Speech Recognition

* Audio sent to Deepgram API
* Transcript generated automatically

⸻

Transcript Management

Save Transcript

Stores:

* File Name
* Cloudinary URL
* Transcript Text
* Upload Timestamp

Transcript History

Retrieve all previous transcripts.

Delete Transcript

Delete transcript by ID.

⸻

Cloudinary Integration

Audio files are uploaded to Cloudinary.

Stored information:

* Secure URL
* Public cloud storage

Example URL:

https://res.cloudinary.com/your-cloud-name/raw/upload/sample.mp3

⸻

Database

User Table

Column	Type
id	Long
name	String
email	String
password	String

Transcript Table

Column	Type
id	Long
fileName	String
filePath	String
transcript	Text
uploadedAt	LocalDateTime

⸻

Security Features

Implemented:

* BCrypt Password Encoding
* Validation Annotations
* Global Exception Handling
* Duplicate Email Protection

⸻

API Endpoints

User APIs

Register User

POST

/api/users/register

Request:

{
  "name":"Avantika",
  "email":"avantika@gmail.com",
  "password":"123456"
}

Response:

{
  "id":1,
  "name":"Avantika",
  "email":"avantika@gmail.com"
}

⸻

Login User

POST

/api/users/login

Request:

{
  "email":"avantika@gmail.com",
  "password":"123456"
}

Response:

"Login successful"

⸻

Speech APIs

Upload Audio

POST

/api/speech/upload

Form Data:

file = sample.mp3

Response:

{
  "transcript":"Hello World"
}

⸻

Get History

GET

/api/speech/history

Response:

[
  {
    "fileName":"sample.mp3",
    "filePath":"https://cloudinary-url",
    "transcript":"Hello World",
    "uploadedAt":"2026-05-30T17:22:39"
  }
]

⸻

Delete Transcript

DELETE

/api/speech/delete/{id}

Response:

Transcript deleted successfully

⸻

Validation Implemented

User Entity

Name

@NotBlank(message = "Name is required")

Email

@Email(message = "Invalid email format")

Password

@NotBlank(message = "Password is required")

⸻

Exception Handling

GlobalExceptionHandler handles:

Validation Errors

Example:

{
  "email":"Invalid email format",
  "name":"Name is required"
}

⸻

Duplicate Email

Example:

{
  "error":"Email already exists"
}

⸻

Invalid Credentials

Example:

{
  "error":"Invalid password"
}

⸻

User Not Found

Example:

{
  "error":"User not found"
}

⸻

Invalid File Upload

Example:

{
  "error":"Only audio files allowed"
}

⸻

Environment Variables

Deepgram

deepgram.api.key=YOUR_DEEPGRAM_API_KEY

Cloudinary

cloudinary.cloud_name=YOUR_CLOUD_NAME
cloudinary.api_key=YOUR_API_KEY
cloudinary.api_secret=YOUR_API_SECRET

⸻
<img width="1908" height="1014" alt="Screenshot 2026-05-31 222557" src="https://github.com/user-attachments/assets/ea6eea0f-c9a7-4fe1-adfd-d0c69b36ed71" />
<img width="1914" height="945" alt="Screenshot 2026-05-31 220811" src="https://github.com/user-attachments/assets/9e44067a-3b08-4805-b282-a0edd424d04a" />







⸻

Future Enhancements

JWT Authentication

Replace simple login with JWT token-based authentication.

User-wise Transcript History

Users can see only their own transcripts.

Download Transcript

Export transcript as TXT or PDF.

Transcript Search

Search transcripts by keyword.

Audio Streaming

Support real-time transcription.

Docker Deployment

Containerize backend and database.

Cloud Deployment

Deploy using:

* Render
* Railway
* AWS
* Azure

⸻

Current Status

Backend Completion: ~95%

Completed:

* User Registration
* User Login
* Password Encryption
* Validation
* Exception Handling
* Audio Upload
* Deepgram Integration
* PostgreSQL Integration
* Cloudinary Integration
* Transcript History
* Transcript Delete API

Remaining:

* JWT Authentication
* User-wise History
* Deployment
* Frontend Polishing

⸻

Developed by: Avantika Tagde
Project: Speech-to-Text Application (STT)
