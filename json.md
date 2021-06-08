Hi,

Example JSON response

```json
[
  {
    "key1": "value2",
    "key2": 20,
    "key3": "bar"
  },
  {
    "key1": "value1",
    "key2": 10,
    "key3": "foo"
  }
]
```

it is important to treat "key1" and "key2" together.

My "wrong" Test. I would like to define the key value pairs (all and not all) that belong together, but regardless of the order.

```java
when().get("/api/query").then().statusCode(200)
    .body("$", hasItems(
        contains(
            hasEntry("key2",10),
            hasEntry("key1","value1")
        ),
        contains(
            hasEntry("key2",20),
            hasEntry("key1","value2")
        )
    ));
```