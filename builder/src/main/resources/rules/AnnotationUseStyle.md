
Annotations should only use brackets and named attributes when they are needed. If only the default parameter is specified, then only the attribute value should be given. If there are no parameters, then no brackets should be given.

Valid:
````
@Entity
@Table("names")
@MyAnnotation(realm = "external")
````

Invalid:
````
@Entity()
@Table(value = "names")
````
