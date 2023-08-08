# notification-service

Rate-Limited Notification Service

## Problem

Create a Notification system that sends out email notifications of various types. Limit the number
of emails sent to them by implementing a rate-limited version of NotificationService.

## How to run

- Clone the repo
- Run `mvn spring-boot:run` to start the application

## Solution description

The solution is made up of two components, the sender and the notifier.
The sender is responsible for sending notifications to the notifier.

The notifier is rate-limited via the application.yml properties. To declare a new limiter, one must
modify only this file.

The limiter creation involves two steps:

- declare a new limiter name in the `limiters` property
- declare the time interval and the number of tokens in a section with the limiter name.

Example:

```yaml
limiters: status
status:
  seconds: 5
  tokens: 3
```

This combination means "allow 3 notifications every 5 seconds".

To send a new message via the REST API, the PUT must be made to the `/ratelimiter/seconds` endpoint.

Example body:

```json
{
  "type": "status",
  "userId": 3,
  "message": "This is the message"
}
```

To modify a limiter seconds interval, the PUT must be made to the `/ratelimiter/tokens` endpoint.
Example body:

```json
{
  "type": "news",
  "seconds": 6
}
```

Below is a small description of the iterations.

## First solution iteration

The solution is a Spring Boot application that exposes a REST API to send notifications.

The main internal components are the Notifier, the RateLimiter and the NotifierSystem.

The Notifier is responsible for sending the notification to the user.
Before sending it, it must first acquire a token from the RateLimiter, with the limits set in
the `application.yml` file.
If the RateLimiter is not able to provide a token, the Notifier will not send the notification and
it will show a log.
If it can, it will send the notification, which is also implemented as a simple log.

The NotifierSystem is responsible for creating the Notifier instances and providing them to the REST
API.

The RateLimiter can be configured with four different granularities: per second, per minute, per
hour and per day.
It also has different limits for each type of notification.

The REST API exposes a POST endpoint `/send` that triggers a few example notifications.

## Second solution iteration

The second iteration expands the REST endpoint, allowing to send custom messages. It also adds data
validation.
The rate limiter has now only one value to configure the time limit. This makes it more complex, but
also more flexible, since one can set whatever time interval to receive a message.
It also simplifies the RateLimiter implementation.

The second iteration of the solution adds a new component, the Sender, which is implemented in a
separate package.
It's main task is to simulate constant traffic of notifications.

## Third solution iteration

The third iteration now allows tokens and second limits. It also incorporates endpoints to modify
them via the REST API.
It adds exception global handling and simplifies internal components. It also adds tests,
thought more are needed to fully test the solution.


