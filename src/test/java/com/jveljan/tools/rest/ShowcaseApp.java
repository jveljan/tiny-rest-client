package com.jveljan.tools.rest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jveljan.tools.rest.github.GitRepo;
import com.jveljan.tools.rest.github.GitUser;



public class ShowcaseApp
{
	private void gitGetUser() throws IOException {
    	String url = "https://api.github.com/users/jveljan";
		GitUser resp = Rest.get(url).as(GitUser.class);
        System.out.println("GetUser:" + resp);
	}
	
	private void gitGetUserAsync() throws IOException {
		String url = "https://api.github.com/users/jveljan";
		Rest.get(url).async(new Handler<GitUser>() {
			public void success(GitUser resp) {
				System.out.println("Async result: " + resp);
			}
			public void error(Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	private void gitGetRepos() throws IOException {
    	String url = "https://api.github.com/users/jveljan/repos";
		List<GitRepo> resp = Rest.get(url).as(new TypeReference<List<GitRepo>>(){});
        System.out.println("GetRepos: " + resp);
	}
	
	private void jsontestHeadersMap() throws IOException {
		Map<String, String> myheaders = 
				Rest.get("http://headers.jsontest.com/")
					.as(new TypeReference<Map<String, String>>(){});
		System.out.println("myheaders:" + myheaders);
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
    	ShowcaseApp app = new ShowcaseApp();
    	
    	System.out.println("Call to gitGetUser");
    	app.gitGetUser();
    	
    	System.out.println("Call to gitGetUserAsync");
    	app.gitGetUserAsync();
    	
    	System.out.println("Call to gitGetRepos");
    	app.gitGetRepos();
    	
    	System.out.println("Call to jsontestHeadersMap");
    	app.jsontestHeadersMap();
    	
    	Rest.shutdown();
	}
	
}
