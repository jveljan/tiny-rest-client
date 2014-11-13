package com.jveljan.tools.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Very simple fluid rest client
 * 
 * @author Jovica
 *
 */
public class RestHandler {
	private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();
	static {
		DEFAULT_MAPPER
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
	
	private ObjectMapper mapper = DEFAULT_MAPPER;
	private HttpHeaders headers;
	private URL url;
	private String method;
	private String body;
	private ExecutorService executor;
	public RestHandler(String url, ExecutorService executor) throws MalformedURLException {
		this(null, url, executor);
	}
	public RestHandler(String method, String url, ExecutorService executor) throws MalformedURLException {
		this.method = method;
		this.url = new URL(url);
		this.executor = executor;
		this.headers = new HttpHeaders();
		this.headers.set("Content-Type", "application/json");
		this.headers.set("Accept", "application/json");
	}
	
	public RestHandler mapper(ObjectMapper mapper) {
		this.mapper = mapper;
		return this;
	}
	
	public RestHandler header(String key, String value) {
		headers.set(key, value);
		return this;
	}
	public RestHandler body(String body) {
		this.body = body;
		return this;
	}
	public RestHandler body(Object body) throws JsonProcessingException {
		this.body = this.mapper.writeValueAsString(body);
		return this;
	}
	
	public RestHandler basicAuth(String username, String password) {
		String b = Base64.getEncoder().encodeToString(new String(username + ":" + password).getBytes());
		headers.set("Authorization", "Basic " + b);
		return this;
	}
	
	public <T> T as(Class<T> c) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(exec().getInputStream(), c);
	}
	public <T> T as(TypeReference<T> ref) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(exec().getInputStream(), ref);
	}
	
	public <T> void async(final Handler<T> handler) throws JsonParseException, JsonMappingException, IOException {
		executor.execute(new Runnable() {
			public void run() {
				try {
					handler.success( RestHandler.this.as(handler));
				} catch (Exception e) {
					handler.error(e);
				}
			}
		});
	}
	
	private HttpURLConnection exec() throws IOException {
		final HttpURLConnection conn = openConnection(url, null);
		if(method != null) {
			conn.setRequestMethod(method);
		}
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}
		if(this.body != null) {
			conn.setDoOutput(true);
			final PrintWriter pw = new PrintWriter(conn.getOutputStream());
			pw.write(body);
			pw.flush();
		}
		//todo: response headers?
		return conn;
	}
	
	public String readString() throws IOException {
		final HttpURLConnection conn = exec();
		return Utils.readInputStreamToString(conn.getInputStream());
	}
	
	protected HttpURLConnection openConnection(URL url, Proxy proxy) throws IOException {
		URLConnection urlConnection = (proxy != null ? url.openConnection(proxy) : url.openConnection());
		//Assert.isInstanceOf(HttpURLConnection.class, urlConnection);
		return (HttpURLConnection) urlConnection;
	}
	public String formattedJsonString() throws JsonProcessingException, IOException {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(as(Object.class));
	}
}