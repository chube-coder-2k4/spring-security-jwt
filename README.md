# Spring Security JWT - User Management System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue.svg)](https://spring.io/projects/spring-security)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 📋 Mô tả dự án

Dự án Spring Security JWT là một hệ thống quản lý người dùng hiện đại được xây dựng với Spring Boot và Spring Security, tích hợp JWT (JSON Web Token) để xác thực và phân quyền. Hệ thống cung cấp các tính năng quản lý người dùng đầy đủ với khả năng mở rộng cao.

## ✨ Tính năng chính

### 🔐 Xác thực & Bảo mật
- **JWT Authentication**: Xác thực dựa trên JSON Web Token
- **Role-based Authorization**: Phân quyền theo vai trò (OWNER, ADMIN, USER)
- **Permission Management**: Quản lý quyền chi tiết
- **Email Verification**: Xác thực email người dùng

### 👥 Quản lý người dùng
- **CRUD Operations**: Tạo, đọc, cập nhật, xóa người dùng
- **User Profiles**: Quản lý thông tin cá nhân chi tiết
- **Address Management**: Quản lý địa chỉ người dùng
- **Status Management**: Quản lý trạng thái người dùng (ACTIVE/INACTIVE/NONE)

### 🔍 Tìm kiếm & Phân trang
- **Advanced Search**: Tìm kiếm nâng cao với nhiều tiêu chí
- **Pagination**: Phân trang và sắp xếp dữ liệu
- **Multi-column Sorting**: Sắp xếp theo nhiều cột

### 📧 Hệ thống Email
- **Email Templates**: Template email với Thymeleaf
- **Confirmation Emails**: Email xác nhận đăng ký
- **File Attachments**: Gửi email với file đính kèm

### 🌐 Tính năng khác
- **Internationalization**: Hỗ trợ đa ngôn ngữ (EN, FR)
- **API Documentation**: Tài liệu API với OpenAPI/Swagger
- **CORS Configuration**: Cấu hình CORS cho frontend
- **Docker Support**: Hỗ trợ containerization

## 🛠 Công nghệ sử dụng

- **Java 17**: Ngôn ngữ lập trình chính
- **Spring Boot 3.x**: Framework chính
- **Spring Security**: Bảo mật và xác thực
- **Spring Data JPA**: Tương tác với cơ sở dữ liệu
- **JWT**: JSON Web Token cho xác thực
- **Thymeleaf**: Template engine cho email
- **OpenAPI/Swagger**: Tài liệu API
- **Maven**: Quản lý dependencies
- **Docker**: Containerization

## 📁 Cấu trúc dự án

```
src/
├── main/
│   ├── java/vn/tayjava/
│   │   ├── configuration/     # Cấu hình Spring
│   │   ├── controller/        # REST Controllers
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── exception/        # Exception handling
│   │   ├── model/           # Entity models
│   │   ├── repository/      # Data repositories
│   │   ├── service/         # Business logic
│   │   └── util/           # Utilities
│   └── resources/
│       ├── templates/       # Email templates
│       └── application.yml  # Configuration
└── test/                   # Unit tests
```

## 🚀 Cài đặt và chạy dự án

### Yêu cầu hệ thống
- Java 17+
- Maven 3.6+
- MySQL/PostgreSQL (hoặc database tương thích)

### 1. Clone repository
```bash
git clone https://github.com/chube-coder-2k4/spring-security-jwt.git
cd spring-security-jwt
```

### 2. Cấu hình database
Cập nhật file `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your_username
    password: your_password
```

### 3. Cấu hình email
```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: your_email@gmail.com
    password: your_app_password
```

### 4. Build và chạy
```bash
# Build project
mvn clean install

# Chạy ứng dụng
mvn spring-boot:run
```

### 5. Chạy với Docker
```bash
# Build Docker image
docker build -t spring-security-jwt .

# Chạy container
docker run -p 8080:80 spring-security-jwt
```

## 📚 API Documentation

Sau khi khởi động ứng dụng, truy cập Swagger UI tại:
```
http://localhost:8080/swagger-ui.html
```

### Các endpoint chính:

#### User Management
- `POST /user/` - Tạo người dùng mới
- `GET /user/{userId}` - Lấy thông tin người dùng
- `PUT /user/{userId}` - Cập nhật người dùng
- `DELETE /user/{userId}` - Xóa người dùng
- `GET /user/confirm/{userId}` - Xác nhận email

#### Common Services
- `POST /common/send-email` - Gửi email

## ⚙️ Thiết lập Gmail

Để sử dụng tính năng gửi email qua Gmail:

1. **Bật xác thực 2 bước**: [Google 2-Step Verification](https://myaccount.google.com/signinoptions/two-step-verification/enroll-welcome)
2. **Tạo App Password**: [Google App Passwords](https://myaccount.google.com/apppasswords)
3. **Cấu hình trong application.yml**:
```yaml
spring:
  mail:
    username: your_email@gmail.com
    password: your_app_password
```

## 🔒 Bảo mật

- JWT tokens được sử dụng cho xác thực
- Passwords được mã hóa bằng BCrypt
- CORS được cấu hình cho môi trường development
- Validation đầu vào cho tất cả API endpoints

## 🌍 Đa ngôn ngữ

Dự án hỗ trợ internationalization ( i18n ) với:
- English (en)
- French (fr)
- Vietnamese (mặc định)

## 🧪 Testing

Chạy unit tests:
```bash
mvn test
```


## 👨‍💻 Tác giả

**Trần Quang Huy** - *Java Backend*

## 🤝 Đóng góp

Mọi đóng góp đều được chào đón! Vui lòng:

1. Fork repository
2. Tạo feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📞 Liên hệ

Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ qua:
- Email: qhuyddbmt1232gmail.com
- Phone: 0373777412

## Note : Đây là src code mình xây dựng trong quá trình học khóa học 

---

⭐ **Nếu dự án này hữu ích, hãy cho một star nhé!** ⭐
