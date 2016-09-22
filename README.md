# Development

## Build

**NOTE**: Experimental incremental build for Kotlin is enabled. Feel free to modify the values in [`gradle.properties`](./gradle.properties).

    $ gradle --daemon wrapper
    $ ./gradlew --daemon build

## Test

The **JUnit** testing framework is used to write the tests.

    $ ./gradlew --daemon test
