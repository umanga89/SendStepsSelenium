# SendStepsSelenium

This is targeted at people with [Maven](https://maven.apache.org/) experience.

To build it, you will need to download and unpack the latest (or recent) version of Maven (https://maven.apache.org/download.cgi)
Then, you will need to install a Java 1.8 (or higher) JDK (not JRE!)
Add maven and java as system variables
Now you can run `mvn clean install` and Maven will compile your project and run the tests.

How you run this code is up to you, but usually you would start by using an IDE like [NetBeans](https://netbeans.org/), [Intellij IDEA](https://www.jetbrains.com/idea/), or [Eclipse](https://eclipse.org/ide/).

# Running the test suite

You can use maven from the command line. Navigate to root directory of the project which has 'pom.xml' file

* `mvn clean install`: it will run the test suite
* Reports and log file will be created under target folder

If you need more information please take a look at this [quick tutorial](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).
