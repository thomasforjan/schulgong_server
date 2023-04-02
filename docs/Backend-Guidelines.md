# PPR - Schulgong - Backend-Guidelines 

<hr>

## :pushpin: General information

The present document provides an outline of the guidelines for the backend.

<hr>

### :wrench: Environment Setup:

- Java Version 17

- Gradle 7.6.1

- Git (required)

- IntelliJ IDEA JetBrains (Recommended)

<hr>

### :book: Naming convention for variables

- All variables must be written in English!

- In addition, the variables must be in camelCase!

- Meaningful variable names e.g.: totalAmount;

- The naming convention for a list must be for e.g.: personList;

#### Java Doc (Documentation):

- All methods must have English Java documentation e.g.:

```java
/**
 * Reads a text file into ArrayList
 * 
 * @return content of text file
 */
```

- All classes must have at the beginning implementation notes e.g.:

```java
/** 
- @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
- @version 0.1
- @implNote practical project
- @since March 2023
*/
```

### :file_folder: Project structure

- It is necessary to implement each service within its own separate module. 
- We have decided to use the following folder structure (as an example). We have chosen to extract the models to enable multiple modules to access them.
- The directory named "database" serves as the designated location for storing the SQLite files.
```md
├── at.schulgong.rest-api
|  ├── controller
|  ├── dto
|  ├── assembler
|  ├── message
|  ├── util
|  | └── DtoConverter.java
├── model
├── database
```

### :clipboard: Testing
For every implemented class, a corresponding test class and associated JUnit tests must also be written, for example "DtoConverterTest.java".
