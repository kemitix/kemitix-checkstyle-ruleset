
Check that code blocks are surrounded by braces.

Valid:
````
if (obj.isValid()) {
    return true;
}

while (obj.isValid()) {
    return true;
}

do {
    this.notify();
} while (o != null);

for (int i = 0; ;) {
    this.notify();
}
````

Invalid:
````
if (obj.isValid()) return true;

while (obj.isValid()) return true;

do this.notify(); while (o != null);

for (int i = 0; ;) this.notify();
````
