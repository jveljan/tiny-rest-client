package com.jveljan.tools.rest;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Rest fluid client
 *
 *
 
 - example get (blocking call):
 
 Account account = 
 	Rest.get("example/account/1/details")
 		.basicAuth("user","pass")
 		.as(Account.class)
 
  
 - example async call:
 
	Rest.post("https://android.googleapis.com/gcm/send")
		.header("Authorization", "key=apikeu")
		.body(msg)
		.async(new Handler<GCMMessageResponse>() {
			public void success(GCMMessageResponse resp) {
				// handle HTTP 200 response and valid parse
			}
			public void error(Exception e) {
				//handle error
			}
		});

 * 
 * @author Jovica
 *
 */
public class Rest {
	private static ExecutorService executor = Executors.newFixedThreadPool(5);
	public static void shutdown() {
		executor.shutdown();
	}
	private static RestHandler createRestHandler(String method, String url) throws MalformedURLException {
		return new RestHandler(method, url, executor);
	}
	public static RestHandler get(String url) throws MalformedURLException {
		return createRestHandler("GET", url);
	}
	public static RestHandler post(String url) throws MalformedURLException {
		return createRestHandler("POST", url);
	}
	public static RestHandler put(String url) throws MalformedURLException {
		return createRestHandler("PUT", url);
	}
	public static RestHandler delete(String url) throws MalformedURLException {
		return createRestHandler("DELETE", url);
	}
}
