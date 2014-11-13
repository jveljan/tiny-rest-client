Java Tiny Rest Client
================
Fluent API.
================
Example 1:
```java
Account account = 
 	Rest.get("example/account/1/details")
 		.basicAuth("user","pass")
 		.as(Account.class)
```
================
Example 2:
```java
Rest.post("https://android.googleapis.com/gcm/send")
		.header("Authorization", "key=apikeu")
		.body(new GCMRequest("rrrr"))
		.async(new Handler<GCMResponse>() {
			public void success(GCMResponse resp) {
				// handle HTTP 200 response and valid parse
			}
			public void error(Exception e) {
				//handle error
			}
		});
```