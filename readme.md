# How to test - in  Java

# Testing in Java part 1

In this article, I will try to cover all the basics for building tests in our Java projects.

# Main

It's not the recommended way to test but yes, sometimes it's easy to run a main to test some simple code, or maybe something we don't remember.  

```java
public static void main(String[] args) {
    var b1 = new BigDecimal("0.2");
    var b2 = new BigDecimal(0.2);
    var b3 = BigDecimal.valueOf(1/5f);

    System.out.println(b1);
    System.out.println(b2);
    System.out.println(b3);

    System.out.println(b1.equals(b2));
    System.out.println(b1.equals(b3));
    System.out.println(b2.setScale(2, RoundingMode.HALF_DOWN));
    }
```

Output: 
```
0.2
0.200000000000000011102230246251565404236316680908203125
0.20000000298023224
false
false
0.20
```
Can we run them in an online editor? Yes, sure. Find one that you can select JDK version.

# Junit

What is Junit? It's a framework that provides the foundations, the core, for launching testing frameworks. It defined TestEngine API for developing testing frameworks.  **This means that Junit is the very basic foundations for Testing in Java.** 

> It was written by Kent Beck and Erich Gamma during a flight from Zurich to Atlanta, based on the SUnit (Smalltalk unit testing framework)
 
Current version is Junit5, it requires Java8+ to run. But it can run Junit4 tests. Pay attention to the JDK required by your project: Java is more than 20 years old and many-many lines of code were written. So it might be the case that in your project, it's needed to maintain some legacy code.  This Guide is for Junit 5, but here some JUnit4 migration for you:




## Get Started: Dependencies

This is the dependency for your project to run Junit tests. 

```xml
<dependencyManagement>
  <dependencies>   
    <dependency>
      <groupId>org.junit</groupId>
      <artifactId>junit-bom</artifactId>   <!-- This BOM defines all the artifacts -->
      <version>5.9.2</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <!-- <version>5.9.2</version>  you can leave it out if you use the BOM above  or you can define the version you might need --> 
    <scope>test</scope>       <!-- very important:: This dependency is only needed for test execution -->
</dependency>
```

Again for maintainers, you can see if your project contains it by running:

```bash
# unix 
mvn  dependency:tree | grep -i junit

# windows cmd
mvn  dependency:tree | findstr /i junit

# windows powershell 🤔
mvn  dependency:tree | Select-String -NotMatch junit
```

## Structure

Java (or maven really) provides a good [project structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) - I think it was originated by maven, but I can't tell for sure.

This is how it's strcutured:

### Main application code
```
src/main/java		
src/main/resources	
src/main/filters	
```

### Tests 
```
src/test/java		
src/test/resources	
src/test/filters
```

I said "Ideally" because it's not "mandatory" to have testing code outside your main application code. Thus, you might forget 😮 that you've dome some `main` inside a class to have a quick test. 

> Please don't put testing code inside your main sources. 🫵

### Maven Surefire Plugin

It's the default Maven plugin that has the `test` goal. 

As it says in the doc https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven-filter-test-class-names, by default the Maven Surefire Plugin will scan for test classes whose fully qualified names match the following patterns.

```
**/Test*.java
**/*Test.java
**/*Tests.java
**/*TestCase.java
```

Pay attention to the ** at the beginning: it means that Test classes can be anywhere !! 😮

Moreover, it will include all nested classes (including static member classes) by default. 

### Integration tests  

As explain in my `Software testing` webinar, Unit Testing is used when **Testing in Isolation**, whilst Integration Testing is to test one or more things. 

The *Isolation* part is not physical: you can think as it as method, a class, a service. To me, it's when the code you're testing is noy interacting with anything outside it. 

That's for theory. In practice, the difference is the plugin Maven uses to run them and the behavior they have.  

By default, the **Maven Surefire Plugin** executes unit tests during the test phase, while the **Failsafe* plugin runs integration tests in the integration-test phase. [Taken from here](https://www.baeldung.com/maven-integration-test#failsafe)

> The **Failsafe Plugin** is designed to run _integration_ tests while the **Surefire Plugin** is designed to run unit tests. [Reference](https://maven.apache.org/surefire/maven-failsafe-plugin/)


Based on https://maven.apache.org/surefire/maven-failsafe-plugin/integration-test-mojo.html#includes, only the below file patterns will be considered for integration test. 

```xml
<includes>
    <include>**/IT*.java</include>
    <include>**/*IT.java</include>
    <include>**/*ITCase.java</include>
</includes>
```
If you want to change this minimal integration testing support, please refer to https://stackoverflow.com/a/38398474

You'll talk about Integration Tests later.

## Writing tests

Every method with the `@Test` annotation under `src/test/java` (*) directory is executed as a test by maven and considers as such by your IDE.  

> As said before, place your tests in `src/test/java`

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import example.util.Calculator;
import org.junit.jupiter.api.Test;

class MyFirstJUnitJupiterTests {

    private final Calculator calculator = new Calculator();

    @Test
    void addition() {
        assertEquals(2, calculator.add(1, 1));
    }

}
```

## How is it tested ??

Ok, nice, but how can I run my tests ?





### Useful Annotations

- @Test: Main testing annotation
- @DisplayName: descriptive test
- @BeforeAll, BeforeEach, AfterAll, AfterEach: method to be execute before or after each or all
- @Disable: Force to disable a test
- @Tag: to filter test execution like in `mvn -Dgroups="integration, fast, feature-168"`  or `mvn -DexcludedGroups="slow"` (taken from https://mkyong.com/junit5/junit-5-tagging-and-filtering-tag-examples/)
- @ParameterizedTest, @RepeatedTest, @Timeout

### Assertiongs

https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html










----
# Mockito (jMock, EasyMock)



# PowerMock

# SpringTest

# JaCoCo
