# CUTE Notification Service

## Description
The CUTE Notification Service is a full-stack (Spring Boot + React) app built to showcase my skills.

The app simulates a service that routes email, SMS and push notifications to other interested services through AMQP queues
based on requests made from a front-end app. This service utilizes the strategy design pattern to enable flexible notification 
handling and can be easily integrated into larger systems.

This is a Docker Composed app, so a RabbitMQ service, the back-end and the front-end are started simultaneously by using a 
Docker Compose command (see more in the How to Run section). The app also leverages in-memory caching through Caffeine and 
persistence through an H2 database that is populated on app startup.

## Table of Contents
- [How to Run](#how-to-run)
- [How to Use](#how-to-use)
- [How to test](#how-to-test)
- [Considerations](#considerations)

## How to Run
To run the CUTE Notification Service, follow these steps:

1. Clone the repository:
    ```sh
    git clone https://github.com/pvargasc/cute-notification-service.git
    ```
2. Navigate to the project's root directory:
    ```sh
    cd cute-notification-service
    ```
3. Build the project using Docker Compose:
    ```sh
    docker compose up --build
    ```
4. Wait for the apps to finish starting up.

## How to Use
To use the CUTE Notification Service, follow these steps:

1. An in-memory H2 database console is exposed at [/h2-console](http://localhost:8080/h2-console). You can 
login using the following credentials:
   * **URL**: jdbc:h2:mem:testdb
   * **Username**: sa
   * **Password**: password
2. A RabbitMQ management console is exposed in port [15672](http://localhost:15672/). You can
login using the following credentials:
   * **Username**: guest
   * **Password**: guest
3. The React webapp is exposed in port [3000](http://localhost:8080/3000). Play around with the app:
   1. Select a Category from the dropdown and write a message, then press the "Send Notification!" button.
      The notifications you just sent will show in the "Notifications" table since the notifications are
      sorted from most recent to oldest by default.
   2. You can search for any notification using the search bar on top of the "Notifications" table.
   3. Go to the H2 database console and validate that the new notifications were persisted.
   4. Go to the RabbitMQ management console and validate that the new notifications messages were delivered
      their respective queues.

## Considerations
- This is a CUTE little not-prod-ready project and thus there are several obvious violations that can sadden your soul
  a little bit: 
   * Secrets are not externalized since both the database and the message broker are ephemeral.
   * The app uses an in-memory cache as opposed to a centralized cache service like Redis.
   * The app has no environment profiles (dev, qa, stage, prod).
   * No branching strategy was used.
   * No static analysis or testing pipelines were added.
