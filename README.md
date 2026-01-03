# OrderSystem

## System Overview

The OrderSystem is a distributed system designed to handle order processing with a focus on reliability and consistency. It leverages a microservices architecture, utilizing Kafka for asynchronous messaging and TCC (Try-Confirm-Cancel) pattern for distributed transactions.

![System Overview](System%20Overview.png)

## Components

The system consists of the following main components:

### 1. OrderApi
- **Role**: Entry point for order creation and management.
- **Responsibilities**:
    - Receives order requests from clients.
    - Validates requests.
    - Persists initial order state.
    - Publishes order events to Kafka.
    - Implements TCC (Try-Confirm-Cancel) endpoints for distributed transaction coordination.
- **Key Endpoints**:
    - `POST /api/v1/order`: Create a new order.
    - `POST /api/v1/order/try`: Try phase of TCC.
    - `POST /api/v1/order/confirm`: Confirm phase of TCC.
    - `POST /api/v1/order/cancel`: Cancel phase of TCC.

### 2. OrderStatusApi
- **Role**: Manages the lifecycle and status of orders.
- **Responsibilities**:
    - Stores and retrieves order status.
    - Provides APIs to create, update, and query order status.
- **Key Endpoints**:
    - `POST /api/v1/order`: Create or get order status.
    - `PUT /api/v1/order`: Update order status.

### 3. OrderProcessingConsumer
- **Role**: Asynchronous order processor.
- **Responsibilities**:
    - Consumes order events from Kafka (`order-processing-group`).
    - Validates order status with `OrderStatusApi`.
    - Coordinates with `ItemService` (external or internal service) to lock/reserve items.
    - Updates order status to `ORDER_PROCESSING`.
    - Handles errors and potential compensation logic.

## Workflow

1.  **Order Creation**:
    - A client sends an order request to `OrderApi`.
    - `OrderApi` saves the order and sends a message to the Kafka topic.

2.  **Order Processing**:
    - `OrderProcessingConsumer` listens to the Kafka topic.
    - Upon receiving a message, it checks the current status of the order via `OrderStatusApi`.
    - If the status is valid (`ORDER_CREATED`), it proceeds to process the order.
    - It communicates with `ItemService` to reserve items (TCC pattern supported via `try`, `confirm`, `cancel` endpoints).
    - It updates the order status to `ORDER_PROCESSING`.

## Technologies

- **Java / Spring Boot**: Core framework for microservices.
- **Kafka**: Message broker for asynchronous communication.
- **Feign Client**: For inter-service communication (e.g., `ItemServiceClient`).
- **Lombok**: To reduce boilerplate code.
- **JPA / Hibernate**: For database interactions.

## Architecture Patterns

- **Microservices**: Decoupled services for scalability.
- **Event-Driven**: Using Kafka for loose coupling between order creation and processing.
- **TCC (Try-Confirm-Cancel)**: Implemented for distributed transactions to ensure data consistency across services (Order and Item services).
