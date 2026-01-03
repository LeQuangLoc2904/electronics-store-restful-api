## Project Description
The Electronics Store RESTful API is a backend service designed to manage an electronics store's operations. It provides endpoints for managing products, users, orders, and other related entities. Built using Spring Boot, the project follows a layered architecture and incorporates features like validation, exception handling, and object mapping.

### Key Features
- Manage products, including attributes, images, and categories.
- Handle user operations, including authentication and authorization.
- Support for creating and managing orders and carts.
- Comprehensive exception handling and validation.
- Token-based authentication using JWT.

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.6+
- A relational database (e.g., MySQL, PostgreSQL)

### Steps to Run Locally
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd electronics-store-restful-api
   ```

2. Configure the database:
   - Update the `src/main/resources/application.yml` file with your database credentials.

3. Build the project:
   ```bash
   ./mvnw clean install
   ```

4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## License
This project is licensed under the MIT License. See the LICENSE file for details.
