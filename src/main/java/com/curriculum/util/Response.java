package com.curriculum.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
	public Integer code;
	public String message;
	public Object data;
}
