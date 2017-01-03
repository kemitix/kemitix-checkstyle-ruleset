
Prevents importing static members, unless they are one of the following:

* `org.assertj.core.api.Assertions.assertThat`
* `org.mockito.BDDMockito.given`
* `org.mockito.Mockito.*`
* `org.mockito.Matchers.*`
* `org.mockito.Mockito.*`

Valid:
````
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
````

Invalid:
````
import static java.nio.charset.StandardCharsets.UTF_8;
````
