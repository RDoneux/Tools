# LogSystem
custom logger providing functionality for debugging post-release. 

## Getting Started
The project can be included through maven by using the jitpack.io repository to package the github project.
Include the following repository in the POM.xml file:

```
<repository>
  <id>jitpack.io</id>
  <url>https://jitpack.io</url>
</repository>
```

The project can then be added as a dependancy:

```
<dependency>
  <groupId>com.github.RDoneux</groupId>
  <artifactId>LogSystem</artifactId>
  <version>v1.0</version>
</dependency>
```

### Examples

creating a log line:
```
Log.out("this is the line to log");
```
output:
```
[LOG:16:58:42:692625800]: this is the line to log
```

setting the log level:
```
Log.level(Level.WARNING);
Log.out("this is the warning line");
```
output:
```
[WARNING:16:58:42:692625800]: this is the warning line
```
