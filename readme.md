# How to test - in  Java

# Testing in Java part 1

In this article, I will try to cover all the basics for building tests in our Java projects. I will mainly use Maven, but Gradle has the same functionality and support all of the concepts detailed here.  

# Main

It's far from the recommended way to test. But yes, sometimes it's easy to run a `main` to test some simple code, or maybe something we don't remember.

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
> *TIP*: The code above can be run them in an online editor. Find one that you can select JDK version.

# JUnit

What is JUnit? It's a framework that provides the foundations -the core- for creating and running tests. It defines a TestEngine API for developing testing frameworks.  **This means that JUnit is the very basic foundations for Testing in Java.** 

> It was written by Kent Beck and Erich Gamma during a flight from Zurich to Atlanta, based on the SUnit (Smalltalk unit testing framework)
 
Current version is JUnit5, it requires Java8+ to run. But it can run Junit4 tests. Pay attention to the JDK required by your project: Java is more than 20 years old and many-many lines of code were written. So it might be the case, that in your specific project, it's needed to maintain some legacy code.  This guide is for Junit 5, but JUnit4 can run most of the features shown here.


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

Again for maintainers, you can see if your project contains it by running the following command:

```bash
# unix 
mvn  dependency:tree | grep -i junit

# windows cmd
mvn  dependency:tree | findstr /i junit

# windows powershell 🤔
mvn  dependency:tree | Select-String -NotMatch junit
```

## Structure

Luckly for us in most Java projects, we have a good [project structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) - I think it was originated by Maven, but I can't tell for sure.

This is how it's structured:

### Main application code
```
src/main/java		
src/main/resources	
src/main/filters	
```

### Tests application code
```
src/test/java		
src/test/resources	
src/test/filters
```

I said "Ideally" because it's not "mandatory" to have testing code outside your main application code. Thus, you might forget 😮 that you've dome some `main` inside a class to have a quick test. 

> ## Don't put testing code inside your main sources. 😵

## <a name="surefire"></a> Maven test: Surefire Plugin

It's the default Maven plugin that executes the `test` goal. 

As it says in the doc https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven-filter-test-class-names, by default the Maven Surefire Plugin will scan for test classes whose fully qualified names match the following patterns.

```
**/Test*.java
**/*Test.java
**/*Tests.java
**/*TestCase.java
```

Pay attention to the `**` at the beginning: it means that Test classes can be anywhere !! 😮 In this project, you can look at the MainServiceTest

## Writing tests

Every method with the `@Test` annotation under `src/test/java` (*) directory is executed as a test by maven and considers as such by your IDE.  

> (*) As said before, place your tests in `src/test/java`

This is how a test looks like:

```java 
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MyFirstJUnitJupiterTests {

  @Test
  public void addition() {
    assertEquals(2, Integer.parseInt("1")+1);
  }

}
```

Some notes of the code above:

- `import static` is a way in Java to create *global  alias* to functions local to the file, so `assertEquals` in this case will be a "global" function in this file. 
- No annotation at class level, only the `@Test` is required.

This code is posted in this project.

## A real non-spring test

Consider the following "encryption" method (very weak, don't use for real).

## EncryptService

```java
public class EncryptService {

  private final String KEY = "SECRET";

  public String encryptPassword(String password) {
    return password+KEY;
  }

  public String decryptPassword(String mangled) {
    return mangled.substring(0, mangled.length()-KEY.length());
  }

}
```
Once your test is created with you favourite IDE, it will look like this after writing some tests:

```java
class EncryptServiceTest {

  private EncryptService encryptService;

  /***
   * Tests are classes and can have all classes things: relations, initialization, inheritance.
   */
  public EncryptServiceTest() {
    this.encryptService = new EncryptService();
  }

  @Test
  @DisplayName("Positive test")
  void testEncryptPositive() {
    String password = "mypassword";
    assertEquals(this.encryptService.encryptPassword(password), password+ EncryptService.KEY);
  }

  @Test
  @DisplayName("Negative test")
  void testEncryptNegative() {
    String password = "mypassword";
    assertNotEquals(this.encryptService.encryptPassword(password), password);
  }


}
```

> For a reference on annotations you can look [here](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)

## How is it tested ??

You can run your tests directly from your IDE. But due to the Maven lifecycle, the Surefire plugin is triggered by the `test` lifecycle and will execute all the test as described in the section [Maven test: Surefire Plugin](#surefire)

## Useful Annotations

- @Test: Main testing annotation
- @DisplayName: descriptive test
- @BeforeAll, BeforeEach, AfterAll, AfterEach: method to be execute before or after each or all
- @Disable: Force to disable a test
- @Tag: to filter test execution like in `mvn -Dgroups="integration, fast, feature-168"`  or `mvn -DexcludedGroups="slow"` (taken from https://mkyong.com/junit5/junit-5-tagging-and-filtering-tag-examples/)
- @ParameterizedTest, @RepeatedTest, @Timeout

## Junit 4 vs 5: Annotations

Yes, they've changed the annonations names. Here the cheatsheet:

| JUnit4 | Junit5        |
|--------|---------------|
| @RunWith | @ExtendWith   |
| @Before | @BeforeEach   |
| @After | @AfterEach    |
| @BeforeClass | @BeforeAll    |
| @AfterClass | @AfterAll     |
| @Ignore | @Disabled     |
| @Category | @Tag          |
|  @Rule<br/>@ClassRule | 😵            |

## Junit 5 new Annotations

- @DisplayName
- @ParameterizedTest
- @ValueSource(strings = {"foo", "bar"})
- @NullAndEmptySource
- @NullSource
- @EmptySource

## Assertiongs

There is a good list of assertions and you can take a look at them in the following link: 
https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html

# Mockito (jMock, EasyMock)

As explained before (and also in my previous Webinar), Unit testing should be test in the most ***isolation*** possible. How do I test my code in isolation if it has dependencies with other services/classes/objects ?? 

That's where Mocking comes into picture: create facade objects and provide them the behavior we want for our test. So, we have leveraging libraries and framework that run over JUnit that let us mimic the responses we want for our tests. 

## Mocking

What we want to mock is not the service we want to test, but whatever its dependencies are. So considering the following service. 

```java
@Component
@AllArgsConstructor
public class ManglingService {

  static final String SALT = "SALTKEY";

  // Injected bean
  RemoteMD5Client remoteMD5Client;

  public String saltedMD5(String value) {
    return remoteMD5Client.md5sum(value + SALT);
  }

}
```

The `RemoteMD5Client` is the dependency we want to mock. 



# PowerMock






# Integration tests

As explain in my `Software testing` webinar, Unit Testing is used when **Testing in isolation**, whilst Integration Testing is to test one or more things.

The *Isolation* part is not physical: you can think as it as method, a class, a service. To me, it's when the code you're testing is ***not*** interacting with anything outside it.

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







----

# SpringTest





# JaCoCo
