# Exemplar Serverless Backend

Backend is implemented as a set of AWS Lambda functions, most with java 8 runtime (except Kinesis Analytics preprocessor and a special lambda to warm up java8 lambda containers).

All java 8 based lambdas shares the same codebase, using Dagger2 as dependency injection framework.


## List of the AWS Lambda functions inside the package

`ai.exemplar.callbacks.oauth.OAuthCallbacksProxyHandler` - OAuth callbacks handler;

`ai.exemplar.callbacks.oauth.ScheduledEventHandler` - OAuth tokens refreshing cron job;

`ai.exemplar.data.ScheduledEventHandler` - External data providers fetching cron job;

`ai.exemplar.upload.KinesisEventSourceHandler` - Kinesis Stream handler to ingest analytics results;

`ai.exemplar.proxy.locations.LocationsProxyHandler` - Locations API microservice;

`ai.exemplar.proxy.analytics.AnalyticsProxyHandler` - Analytics API microservice;


## Building

Builds with the local Gradle Wrapper. Complete Gradle binaries and all project dependencies will be downloaded automatically.

Command to build application:
```
gradlew clean build
```

Then pick up a zip distribution in `build/distr` directory and upload it to AWS.


## Integration Jobs

There are a set of integration jobs (like upload old data, ingest the data into Kinesis and so on) implemented in the form of junit4 unit tests which could be automatically run with Gradle.

Command to run unit tests:
```
gradlew test
```
