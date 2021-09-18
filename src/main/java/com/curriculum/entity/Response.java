package com.curriculum.entity;

import lombok.Data;

@Data
public class Response {
	public Integer code;
	public String message;
	public Object data;
}
