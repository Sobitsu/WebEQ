/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sobit.webeq.services.users.rest.exceptions;

/**
 *
 * @author Владелец
 */
public enum Errors {	
	NOT_AUTHORIZED(4010, "Not Authorized"),
	FORBIDDEN(4030, "Forbidden"),
	NOT_FOUND(4040, "Not Found."), 
	SERVER_ERROR(5001, "Server error during operation.");

	private final int code;
	private final String description;

	private Errors(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}
        @Override
	public String toString() {
		return code + ": " + description;
	}
}