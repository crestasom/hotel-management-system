package com.cretasom.hms.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4167782973786323987L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
