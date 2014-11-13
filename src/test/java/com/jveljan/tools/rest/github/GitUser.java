package com.jveljan.tools.rest.github;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitUser {
	private Long id;
	private String url;
	private String type;
	@JsonProperty("created_at")
	private Date createdAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "GitUser [id=" + id + ", url=" + url + ", type=" + type
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
