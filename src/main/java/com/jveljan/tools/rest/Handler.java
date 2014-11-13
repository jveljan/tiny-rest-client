package com.jveljan.tools.rest;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Handler helper used in async calls
 * 
 * @author Jovica
 *
 * @param <T>
 */
public abstract class Handler<T> extends TypeReference<T> {
	public abstract void success(T resp);
	public abstract void error(Exception e);
}
