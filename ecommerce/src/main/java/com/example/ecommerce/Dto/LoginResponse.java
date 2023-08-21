package com.example.ecommerce.Dto;

public class LoginResponse {

	private boolean error;
	String msg;
	long id;
	
	public LoginResponse(boolean error, String msg, long id) {
		super();
		this.error = error;
		this.msg = msg;
		this.id = id;
	}
	public LoginResponse() {
		super();
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
