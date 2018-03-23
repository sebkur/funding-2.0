# Processing project

## Requirements

In order to run the software from the development tree you need a Java
Development Kit (JDK), Version 8 or later. The project uses Gradle as a
build tool, but you should use the included Gradle Wrapper for building
the project.

On Debian-based systems such as Ubuntu or Mint, you can install the JDK
like this:

    sudo apt-get install openjdk-8-jdk

## Command line interface (CLI)

### Building the CLI module

First, make sure to change to the `java` directory, i.e. type

    cd java

Run the Gradle `createRuntime` task to build the CLI:

    ./gradlew clean createRuntime

### CLI usage and tasks

This project has a main executable that can be executed like this:

    ./scripts/funding-cli <task>

Alternatively, add the `scripts` directory to your `PATH` environment
variable in order to run `funding-cli` without specifying its location
each time. The following examples assume you have done that:

    export PATH=$PATH:$(readlink -f scripts)

Then invoke the main executable like this:

    funding-cli <task>

Where `<task>` can be any of the following:

    list
    sort
    clean-up-whitespace
    find-strange-characters
    check-links
    create-html
    export-funding-sources

Each task accepts its own set of command line parameters. To generate the
website you would type:

    funding-cli create-html --output /tmp/some/location
