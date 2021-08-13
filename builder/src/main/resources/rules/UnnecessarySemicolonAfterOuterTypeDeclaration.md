
Checks if unnecessary semicolon is used after type declaration.

Valid:
````java
class A {

   class Nested {

   }; // OK, nested type declarations are ignored

}

interface B {

}

enum C {

}

@interface D {

}
````

Invalid:
````java
class A {

   class Nested {

   }; // OK, nested type declarations are ignored

}; // violation

interface B {

}; // violation

enum C {

}; // violation

@interface D {

}; // violation
````
