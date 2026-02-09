# User Management Application

A **Spring Boot REST API** that provides complete **User Management** functionality with **JWT-based Authentication** and **Role-Based Access Control (RBAC)**.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- User Registration
- User Login with JWT Authentication
- Secure APIs using JWT
- Role-Based Access Control (ADMIN / USER)

### 👤 User Management
- View current authenticated user
- Update user profile
- Delete own account

### 🛠️ Admin Management
- Get all users
- Get user by ID
- Update user details
- Update user status (Enable / Disable)
- Delete any user

---

## 🧩 API Endpoints

### Authentication Management
| Method | Endpoint | Description |
|------|---------|------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | User login (returns JWT token) |

---

### Admin Management (ADMIN only)
| Method | Endpoint | Description |
|------|---------|------------|
| GET | `/api/admin/users` | Get all users |
| GET | `/api/admin/users/{id}` | Get user by ID |
| PUT | `/api/admin/users/{id}` | Update user details |
| PATCH | `/api/admin/users/{id}/status` | Update user status |
| DELETE | `/api/admin/users/{id}` | Delete a user |

---

### User Management
| Method | Endpoint | Description |
|------|---------|------------|
| GET | `/api/user` | Get current authenticated user |
| DELETE | `/api/user` | Delete current authenticated user |

---

## 🔑 JWT Authentication Flow
1. Register a user  
      POST /api/auth/register
2. Login  
      POST /api/auth/login
3. Copy the **JWT token** from the response
4. Open **Swagger UI**
5. Click **Authorize**
6. Enter token
7. Access secured APIs
<img width="1908" height="888" alt="image" src="https://github.com/user-attachments/assets/58f59b5b-5031-45c1-b535-66067a91b010" />
