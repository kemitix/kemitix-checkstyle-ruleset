
Checks that all the values of an `enum` follow the same naming pattern.

Valid:
````
public enum EnumOne {
    FirstElement, SecondElement, ThirdElement;
}

public enum EnumTwo {
    FIRST_ELEMENT, SECOND_ELEMENT, THIRD_ELEMENT;
}
````

Invalid:
````
public enum EnumThree {
    FirstElement, SECOND_ELEMENT, ThirdElement;
}
````
