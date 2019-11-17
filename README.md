###### Project: Automated Traffic Counter <br/> Author: Enrico Caporali <br/> Date Submitted: 18 November, 2019

# Automated Traffic Counter

## Assumptions
The main assumptions taken while developing this application are as follow:

- Readability has been preferred over performance considering that the `MetricService` could 'save' execution time by combine
the traversal of the input file content.   
- 'The 1.5 hour period with least​ cars (i.e. 3 contiguous half hour records)': the output of this task shows 3 lines containing
a timestamp and the total cars count each.
- As stated in the task, input validation was not required because the files are machine-generated.

## Development
- The application is written in Java 8 and it leverages its functional approach. No additional external libraries are used throughout the implementation 
  of this application. The chosen build and dependency management tool is `Gradle`.
- Testing is done by using two standard libraries such as `jUnit` and `Mockito`.

### How to build the project and run unit tests
```
./gradlew clean build
```

### How to run the application
```
./gradlew run
```
