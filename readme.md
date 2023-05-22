# Testing in Java

In this article, I will try to cover all the basics for building tests in our Java projects. I will mainly use Maven, but Gradle has the same functionality and support all the concepts detailed here.  


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
    System.out.println(b1.equals(b3.setScale(2, RoundingMode.HALF_DOWN)));
    }
```

Output: 
```
0.2
0.200000000000000011102230246251565404236316680908203125
0.20000000298023224
false
false
false
```

> The code above can be run them in any online editor. Find one that you can select JDK version.

# JUnit

What is JUnit? It's a framework that provides the foundations -the core- for creating and running tests. It defines a TestEngine API for developing testing frameworks.  **This means that JUnit is the very basic foundations for Testing in Java.** 

> It was written by Kent Beck and Erich Gamma during a flight from Zurich to Atlanta, based on the SUnit (Smalltalk unit testing framework)
 
Current version is JUnit5, it requires Java8+ to run. But it can run Junit4 tests. Pay attention to the JDK required by your project: Java is more than 20 years old and many-many lines of code were written. So it might be the case, that maintaining legacy code is the project objective. This guide is for Junit 5, but JUnit4 can run most of the features shown here, and differences are a few somewhere below this article. 


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

> The BOM actually inserts the referenced POM into your project's POM. 

Again for maintainers, you can see if your project contains it by running the following command:

```bash
# unix 
mvn  dependency:tree | grep -i junit

# windows cmd
mvn  dependency:tree | findstr /i junit

# windows powershell 🤔
mvn  dependency:tree | Select-String -NotMatch junit
```

You can use the `mvn  dependency:tree --debug` if some missing dependency makes the execution fail. 

## Structure

Luckily for us in most Java projects, we have a good [project structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) - I think it was originally proposed by Maven, but I am not able to confirm.

It is very probably that you would find this structure in your maintenance project.

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

This is the ideal project structure. It is not mandatory to have testing code outside your main application code. Thus, you might forget 😮 that you've done some `main` inside a class to have a quick test. 

> ### Don't put Testing code inside your main sources. 😵

## <a name="surefire"></a> Maven test: Surefire Plugin

Ok, we all do the `mvn clean install`, but inside the install goal several things happen, one of them is the `test` goal. Maven execute the `test` goal by running the *Surefire* plugin.


As it says in the [doc](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven-filter-test-class-names), by default the Maven Surefire Plugin will scan for test classes whose fully qualified names match the following patterns.

```
**/Test*.java
**/*Test.java
**/*Tests.java
**/*TestCase.java
```

Pay attention to the `**` at the beginning: it means that `*Test` classes can be anywhere !! 😮 In this project, you can look at the MainServiceTest. This is reason for, let me repeat:

> ### Don't put Testing code inside your main sources. 😵

## Maven test: Surefire Plugin: The importance 

Having the Surefire plugin being executes every time on the Maven life cycle is very important for ***Continuos Integration*** : if a test fails, something is wrong with the code produced and it must not be pushed into a productive release. Really the main idea behind the continues is this: to run automated tests with the latest code and if something is failing, flag it.  

## Writing tests

Every method with the `@Test` annotation under `src/test/java` 👍 directory is executed as a test by maven and is considered as such by your IDE.  


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

Once your test is created with you favourite IDE, it will look like this:

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

  public void nonTestMethod() {
    // This is not a test method, because it does not have the @Test. 
  }
}
```
Method naming is a subject on its own, I've found this article interesting https://medium.com/@stefanovskyi/unit-test-naming-conventions-dd9208eadbea for naming of methods. Very good analysis. I'm following the first suggestion by the author.   

> For a reference on JUnit5 annotations you can look [here](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)

## How is the test executed ??

You can run your tests directly from your IDE. But inside the Maven lifecycle, the Surefire plugin is triggered by the `test` goal, and it will execute all the tests as described in the section [Maven test: Surefire Plugin](#surefire)

## How do you finally know that your result is what you expected? *Assert*

In order to evaluate that our code was executed properly, we have to compare the result of the code being executed to the desired result. 

In JUnit you've got the [Asserts](https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions). The complete list could be found here: 
https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html

## Useful Annotations

These are some everyday used in Junit:

- @Test: Main testing annotation
- @DisplayName: descriptive test 
- @BeforeAll, BeforeEach, AfterAll, AfterEach: method to be execute before or after each or all
- @Disable: Force to disable a test

And here some I've found interesting: 
- @Tag: to filter test execution like in `mvn -Dgroups="integration, fast, feature-168"`  or `mvn -DexcludedGroups="slow"` (taken from https://mkyong.com/junit5/junit-5-tagging-and-filtering-tag-examples/)
- @ParameterizedTest, @RepeatedTest, @Timeout

### Junit 4 vs 5: Annotations

Yes, they've changed the annotations names. Here the cheatsheet:

| JUnit4 | Junit5 |
|--------|--------|
| @RunWith | @ExtendWith |
| @Before | @BeforeEach |
| @After | @AfterEach |
| @BeforeClass | @BeforeAll |
| @AfterClass | @AfterAll |
| @Ignore | @Disabled |
| @Category | @Tag   |
| @Rule<br/>@ClassRule | 😵     |

### Junit 5 new Annotations

- @DisplayName
- @ParameterizedTest
- @ValueSource(strings = {"foo", "bar"})
- @NullAndEmptySource
- @NullSource
- @EmptySource

# Mockito (jMock, EasyMock)

As explained before (and also in my previous Webinar), Unit testing should be executed in the most ***isolation*** possible. How do I test my code in isolation if my code has dependencies with other services/classes/objects/external entities ?? 

That's where Mocking comes into picture: create facade objects and provide them the behavior we want for our test. So, there are leveraging libraries and frameworks that run over JUnit that let us mimic the responses we want for our tests. Mockito, jMock and EasyMock are the most used.

## Mocking

What we want to mock is not the service we want to test, but the dependencies it has. So considering the following service. 

```java
public class ManglingService {

  static final String SALT = "SALTKEY";

  // Injected bean
  RemoteMD5Client remoteMD5Client;

  public String saltedMD5(String value) {
    if(value != null && !("".equals(value))) {
      return remoteMD5Client.md5sum(value + SALT);
    } else {
      return remoteMD5Client.md5sum(value);
    }
  }

}
```

The `RemoteMD5Client` is the dependency we want to mock. So the code will be like this:

```java
@ExtendWith(MockitoExtension.class)
class ManglingServiceTest {

// Initialize mock - The old way
//  public ManglingServiceTest() {
//    MockitoAnnotations.openMocks(this);
//  }

  @Test
  @DisplayName("Testing and mocking a service")
  public void testEncryptSimple() {
    // Mock instance
    RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);

    // Conditions -> Arrange - Given  (Mockito can be statically imported)
    Mockito.when(remoteMD5Client.md5sum("simple")).thenReturn("empty");  

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Assert - Then
    assertEquals(result, "empty" + ManglingService.SALT);
  }
}
```

Some notes of the code above:

- `@ExtendWith` is a way to tell JUnit5 what other extension should it use. In this case, only `Mockito`
- our service to test is not an instance variable, but a local to the method. 

##  Arrange/Act/Assert vs GWT: Given/When/Then

They both have the same principle: to prepare conditions, to execute the code and to evaluate the results. The first is commonly used for Unit testing and TDD, and the later is more frequent in Behavior Driven Development (BDD) context. 

# A word on Processes & Paradigms

Although, these topics are not part of the scope of this article, let's take a "sentence explanatory" approach.

##  Test Driven Development (TDD)

TDD is a software process that instead of start by write code based on a requirement, you start writing test cases. So, the code produced should make pass all the tests. This is an approach or a process instead of a tool on its own. 

##  Behavior Driven Development (BDD)

It's uses the Given-When-Then principle to describe User Stories or Scenarios at high level. BDD more thought for Use Cases and higher level definitions for test cases. It is kind of thought for Product Owner and Business users. It has its own language and it not specific for Java. 

> They are not mutually exclusive.

# Mock vs Spy

A mock object is a full fake object that you have to define all its behaviors. If in our case:

```java
// Mock instance
@Mock
RemoteMD5Client remoteMD5Client;

@Test
@DisplayName("Testing and mocking a service")
public void testEncryptSimple() {
    // Mock instance : Mock Objects can also be create using this Sintax. 
    //RemoteMD5Client remoteMD5Client = Mockito.mock(RemoteMD5Client.class);

    // Conditions -> Arrange - Given  (Mockito can be statically imported)
    Mockito.when(remoteMD5Client.md5sum("simple")).thenReturn("empty");

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("complex");

    // Evaluate the result : Assert - Then
    assertEquals(result, "empty" + ManglingService.SALT);
}
```
 
This will fail, because the mock will only response based on the **Arrange** section we have set. We set for `simple`, but we test for `complex`.

Some notes of the code above:
- The `@Mock` annotation will create an instance mock variable. 

## Spy

Instead of a complete fake object, with Spy you can modify the behavior of an existing object instance. 

```java
  @Spy
  RemoteMD5Client remoteMD5Client;

  @Test
  @DisplayName("Testing and mocking a service")
  public void testEncryptSpy() {
    // Conditions -> Arrange - Given
    Mockito.when(remoteMD5Client.md5sum("simple")).thenReturn("empty");

    // Real service we want to test
    ManglingService manglingService = new ManglingService(remoteMD5Client);

    // The actual Execution ->  Act - When
    String result = manglingService.saltedMD5("simple");

    // Evaluate the result : Then - Assert
    assertEquals(result, "SIMPLE" + ManglingService.SALT);
  }
```

The main difference is that for arguments other than `simple`, the `remoteMD5Client` will code it's original backing code. 





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


--- 
# References

https://methodpoet.com/unit-testing-best-practices/