# 🔐 OTP Based Registration System

A secure **Spring Boot backend application** that implements **OTP-based user registration with email verification**.
Users register using their email, receive a One-Time Password (OTP), and complete the registration after verifying the OTP.

This project demonstrates **secure authentication flow, email integration, and database handling using Spring Boot and JPA**.

---

# 🚀 Features

* OTP based email verification
* Secure user registration flow
* Temporary user storage until OTP verification
* OTP expiration mechanism (5 minutes)
* Gmail SMTP integration for sending OTP emails
* MySQL database integration
* REST API based architecture
* Production-style configuration using **Environment Variables**

---

# 🛠 Tech Stack

Backend

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate

Database

* MySQL

Other Tools

* Maven
* Lombok
* Gmail SMTP

---

# 📂 Project Structure

```
OtpBasedRegistration
│
├── controller
│   └── TestController.java
│
├── service
│   ├── AuthService.java
│   └── EmailService.java
│
├── repository
│   ├── UserRepository.java
│   └── PendingUserRepository.java
│
├── entity
│   ├── User.java
│   └── PendingUser.java
│
├── dto
│   ├── RegisterRequest.java
│   └── OtpVerifyRequest.java
│
└── OtpBasedRegistrationApplication.java
```

---

# 🔄 Registration Flow

1. User sends registration request.
2. System generates a **4-digit OTP**.
3. OTP is stored in `pending_user` table with expiry time.
4. OTP is sent to the user's email.
5. User verifies OTP.
6. After successful verification:

    * User is moved to `user` table.
    * Registration is completed.

---

# 📡 API Endpoints

## 1️⃣ Generate OTP

**POST**

```
/GenrateOtp
```

Request Body

```json
{
  "name": "Vedant",
  "email": "Test@gmail.com",
  "age": 21,
  "password": "12345"
}
```

Response

```
OTP Sent Successfully
```

---

## 2️⃣ Verify OTP

**POST**

```
/verify
```

Request Body

```json
{
  "email": "Test@gmail.com",
  "otp": "1234"
}
```

Response

```
Registration Successful
```

---

# 🗄 Database Tables

### pending_user

Temporary storage until OTP verification.

| Field       | Description    |
| ----------- | -------------- |
| id          | Primary key    |
| name        | User name      |
| email       | User email     |
| age         | User age       |
| password    | User password  |
| otp         | Generated OTP  |
| expiry_time | OTP expiration |

---

### user

Stores successfully registered users.

| Field     | Description       |
| --------- | ----------------- |
| id        | Primary key       |
| name      | User name         |
| email     | User email        |
| age       | User age          |
| password  | User password     |
| create_at | Registration date |
| role      | User role         |

---

# ⚙️ Environment Variables

Sensitive data is stored using environment variables.

Required variables:

```
DB_URL
DB_USERNAME
DB_PASSWORD
MAIL_USERNAME
MAIL_PASSWORD
```

Example:

```
DB_URL=jdbc:mysql://localhost:3306/secureauth?createDatabaseIfNotExist=true
DB_USERNAME=root
DB_PASSWORD=yourpassword

MAIL_USERNAME=yourgmail@gmail.com
MAIL_PASSWORD=your_app_password
```

---

# ▶️ Running the Project

Clone repository

```
git clone https://github.com/your-username/OtpBasedRegistration.git
```

Navigate to project folder

```
cd OtpBasedRegistration
```

Run application

```
mvn spring-boot:run
```

Application will start on:

```
http://localhost:8080
```

---

# 🔐 Security Note

Sensitive credentials like **database password and email password are not stored in the repository**.
They are managed through **environment variables**, which is a recommended production practice.

---

# 📬 Email Configuration

This project uses **Gmail SMTP** to send OTP emails.

SMTP Settings:

```
Host: smtp.gmail.com
Port: 587
TLS Enabled
```

---

# 📈 Future Improvements

* JWT based authentication
* Password encryption using BCrypt
* Resend OTP functionality
* Rate limiting for OTP requests
* User login system

---

# 👨‍💻 Author

Vedant

Backend Developer | Java | Spring Boot

---
