# AMQP Spring Boot

A Spring Boot application demonstrating message queuing using RabbitMQ and AMQP (Advanced Message Queuing Protocol). This project provides a simple REST API for sending messages to a RabbitMQ queue and consuming them asynchronously.

## Features

- **Message Producer**: REST API endpoint to send messages to RabbitMQ queue
- **Message Consumer**: Asynchronous message consumption with configurable concurrency
- **Docker Support**: RabbitMQ setup with Docker Compose
- **Configuration Management**: Externalized configuration for queue settings
- **Retry Mechanism**: Built-in retry logic for failed message processing
- **Dead Letter Queue**: Support for DLQ pattern

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring AMQP** - For RabbitMQ integration
- **RabbitMQ 3.13** - Message broker
- **Lombok** - Reducing boilerplate code
- **Gradle** - Build tool

## Project Structure

```
src/main/java/com/yathdev/amqp_spring_boot/
├── AmqpSpringBootApplication.java    # Main application class
├── config/                           # Configuration classes
├── controller/
│   └── MessageController.java        # REST API endpoints
├── producer/
│   └── MessageProducer.java          # Message publishing logic
├── consumer/
│   └── ConsumerMessage.java          # Message consumption logic
├── model/
│   ├── MessageDto.java               # Message data model
│   └── QueueData.java                # Queue configuration model
└── property/
    └── RabbitMQProperty.java         # RabbitMQ properties
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Docker and Docker Compose
- Gradle (or use the included Gradle wrapper)

### Setup

1. **Start RabbitMQ using Docker Compose**
   ```bash
   docker-compose up -d
   ```
   This will start RabbitMQ with:
   - AMQP port: `5672`
   - Management UI: `http://localhost:15672`
   - Default credentials: `guest/guest`

2. **Build and run the application**
   ```bash
   ./gradlew bootRun
   ```
   
   The application will start on port `8000`.

## Usage

### Send a Message

Send a POST request to publish a message to the queue:

```bash
curl -X POST http://localhost:8000/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "id": "1",
    "content": "Hello, RabbitMQ!"
  }'
```

### Monitor Messages

- **Application Logs**: Check the console output to see published and consumed messages
- **RabbitMQ Management UI**: Visit `http://localhost:15672` to monitor queues, exchanges, and messages

## Configuration

### Environment Variables

You can override RabbitMQ connection settings using environment variables:

- `RABBIT_HOST` (default: localhost)
- `RABBIT_PORT` (default: 5672)
- `RABBIT_USER` (default: guest)
- `RABBIT_PASSWORD` (default: guest)

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/messages` | Send a message to the queue |

### Request Format

```json
{
  "id": "long",
  "content": "string"
}
```

### Response

```
Message sent with ID: {messageId}
```

## Message Processing

- **Producer**: Messages are sent to the `queue_testing_exchange` with routing key `queue_testing_routing_key`
- **Consumer**: Messages are consumed from `queue_testing` queue with a concurrency of 10
- **Retry**: Failed messages are retried with exponential backoff (3s initial, 2x multiplier, max 10s)

## Monitoring

### RabbitMQ Management Interface

Access the RabbitMQ management interface at `http://localhost:15672`:
- Username: `guest`
- Password: `guest`

Here you can:
- Monitor queue depths
- View message rates
- Inspect exchanges and bindings
- Debug message flow
