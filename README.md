# notification-service

Rate-Limited Notification Service

## Problem

Create a Notification system that sends out email notifications of various types. Limit the number
of emails sent to them by implementing a rate-limited version of NotificationService.

## First solution iteration.

The solution is a Spring Boot application that exposes a REST API to send notifications.

The main internal components are the Notifier, the RateLimiter and the NotifierSystem.

The Notifier is responsible for sending the notification to the user.
Before sending it, it must first acquire a token from the RateLimiter, with the limits set in
the `application.yml` file.
If the RateLimiter is not able to provide a token, the Notifier will not send the notification and
it will show a log message.
If it can, it will send the notification, which is implemented as a simple log message.

The NotifierSystem is responsible for creating the Notifier instances and providing them to the REST
API.

The RateLimiter can be configured with four different granularities: per second, per minute, per
hour and per day.
It also has different limits for each type of notification.

The REST API exposes a POST endpoint `/send` that triggers a few example notifications.

## How to run

- Clone the repo
- Run `mvn spring-boot:run` to start the application

