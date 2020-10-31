package com.spring.sonu.Microservice.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserManagementResponse {

	@ApiModelProperty(position = 1, dataType = "String",required = true)
	private String status ="success";
}
