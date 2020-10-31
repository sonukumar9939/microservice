package com.spring.sonu.Microservice.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "User Details Request")
public class UserDetails {

	@NotBlank
	@ApiModelProperty(position = 1,required = true, dataType = "String",example = "Sonu", value = "User Name")
	private String name;
	@NotBlank
	@ApiModelProperty(position = 2,required = true, dataType = "String",example = "20-08-2020", value = "User Date Of Birth")
	private String dob;
	@NotNull
	@ApiModelProperty(position = 3,required = true, dataType = "Decimal",example = "122111241.150", value = "User Salary")
	private BigDecimal salary;
	@NotBlank
	@ApiModelProperty(position = 4,required = true, dataType = "Integer",example = "20-08-2020", value = "User Age")
	private int age;

}
