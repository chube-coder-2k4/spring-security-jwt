```text
 __ __|                  |
    |   _` |  |   |      |   _` | \ \   /  _` |  \ \   /  __ \
    |  (   |  |   |  \   |  (   |  \ \ /  (   |   \ \ /   |   |
   _| \__,_| \__, | \___/  \__,_|   \_/  \__,_| _) \_/   _|  _|
             ____/ Lập Trình Java Từ A-Z
 
   Website: https://tayjava.vn
   Youtube: https://youtube.com/@tayjava 
   TikTok: https://tiktok.com/@tayjava.vn 
```
## Prerequisite
- Cài đặt JDK 17+ nếu chưa thì [cài đặt JDK](https://tayjava.vn/cai-dat-jdk-tren-macos-window-linux-ubuntu/)
- Install Maven 3.5+ nếu chưa thì [cài đặt Maven](https://tayjava.vn/cai-dat-maven-tren-macos-window-linux-ubuntu/)
- Install IntelliJ nếu chưa thì [cài đặt IntelliJ](https://tayjava.vn/cai-dat-intellij-tren-macos-va-window/)

## Technical Stacks
- Java 17
- Maven 3.5+
- Spring Boot 3.2.3
- Spring Data Validation
- Spring Data JPA
- Postgres
- Lombok
- DevTools
- Docker, Docker compose

---
## Thiết lập Gmail
Để cho phép gửi email qua Gmail ta cần thực hiện 2 bước sau

- Step 1: Cho phép xác thực 2 nhân tố: https://myaccount.google.com/signinoptions/two-step-verification/enroll-welcome
- Step 2: Tạo app chỉ định password: https://myaccount.google.com/apppasswords
- Step 3: Gán thông tin vào mail sender

```
spring.mail.username=quoctay87
spring.mail.password=xxx
```