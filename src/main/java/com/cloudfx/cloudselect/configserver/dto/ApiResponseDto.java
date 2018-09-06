package com.cloudfx.cloudselect.configserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDto {

	public static final int SUCCESS = 0;
	public static final int FAILURE = -1;

	private int code;
	private String message;
	private Object data;
	
	public ApiResponseDto(boolean result) {
		if (result) {
			this.code = SUCCESS;
			this.message = "success";
		} else {
			this.code = FAILURE;
			this.message = "failure";
		}
	}
}
