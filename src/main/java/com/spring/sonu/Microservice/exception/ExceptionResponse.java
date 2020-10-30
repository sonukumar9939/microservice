package com.spring.sonu.Microservice.exception;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
	
	@ApiModelProperty(position = 1, required = true, dataType = "Date")
	private LocalDateTime timestamp;
	@ApiModelProperty(position = 2, required = true, dataType = "String")
	private String message;
}
