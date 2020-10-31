package com.spring.sonu.Microservice.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
	
	@ApiModelProperty(position = 1, required = true, dataType = "Date")
	private String status;
	@ApiModelProperty(position = 2, required = true, dataType = "String")
	private String message;
}
