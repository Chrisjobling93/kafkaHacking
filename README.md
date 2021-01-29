# Kotlin Ktor template

## Setup
- Enter the application name in `settings.gradle` file

- Update the directory structure and update the `group` property in build.gradle 

- For sonar update `systemProp.sonar.host.url` property to point to the correct location

# Running the application locally

The gradlewrapper should be used to ensure the application is ran using the expected version of Gradle
-  `./gradlew run`

- For cleaner console logs run the application with the VM Argument `-Dlog4j.configurationFile=src/main/resources/log4j2-dev.yml`
 or use the gradle `run` task which is configured to apply this VM argument.

# Swagger

- Swagger UI resources are generate during a gradle build `./gradlew build`  with the configuration provided in `swagger.json`

- Swagger resources are hosted as static content available through the `/swagger/index.html` url

# Trace Id

- Trace Ids will be passed into the MDCContext from requests into the application where a X-Trace-ID header has been set. 
- Apply the MDCContext() when launching a new child coroutine for the traceId to be passed through the lifecycle of the request
- Whenever a Trace Id is not provided a new one will be generated.