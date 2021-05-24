package com.maguihong.carrental.entity;

public class Response {
	
	private int code;
	
	private String message;
	
	private Object data;
	
	public static Response success(Object data) {
		Response response = new Response();
		response.setCode(0);
		response.setMessage("success");
		response.setData(data);
		return response;
	}
	
	public static Response fail(String message) {
		Response response = new Response();
		response.setCode(1);
		response.setMessage(message);
		return response;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
