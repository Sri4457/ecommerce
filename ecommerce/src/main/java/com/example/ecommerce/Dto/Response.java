package com.example.ecommerce.Dto;

public class Response {

	private boolean error;
	private String msg;
	
	public Response() {
		super();
	}
	public Response(boolean error, String msg) {
		super();
		this.error = error;
		this.msg = msg;
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
	
}
