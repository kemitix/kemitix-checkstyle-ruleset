
Checks that `switch` statements are not nested within one another.

Valid:
````
void doSomething(int a, int b) {

    switch(a) {
        case 1:
            doMore(b);
            break;
        case 2:
            // ..
        }
    }
}

void doMore(int b) {

    switch(b) {
        case 1:
            //
        case 2:
            //
    }
}
````

Invalid:
````
void doSomething(int a, int b) {

    switch(a) {
        case 1:
            switch(b) {
                case 1:
                    //
                case 2:
                    //
            }
            break;
        case 2:
            // ..
        }
    }
}
````
