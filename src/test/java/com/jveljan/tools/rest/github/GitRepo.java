package com.jveljan.tools.rest.github;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitRepo {
	private Long id;
	private String name;
	private String description;
	@JsonProperty("created_at")
	private Date createdAt;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "GitRepo [id=" + id + ", name=" + name + ", description="
				+ description + ", createdAt=" + createdAt + "]";
	}
}
