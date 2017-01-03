
Checks for assignments within an expressions. However, it still allows assignment in a while loop clause.

Valid:
````
while((line = reader.readLine()) != null) {
}
````

Invalid:
````
String s = Integer.toString(i = 2);
````
