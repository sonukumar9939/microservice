package com.spring.sonu.Microservice.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spring.sonu.Microservice.constants.HttpHeaderConstants;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	/*
	 * public static final Contact DEFAULT_CONTACT = new
	 * Contact("User Management Support", "https://userManagement.com",
	 * "support@user.com"); public static final ApiInfo DEFAULT_API_INFO = new
	 * ApiInfo("User Management API Documentation",
	 * "User Management Web service Api Documentation", "v1",
	 * "https://userManagement.com", DEFAULT_CONTACT, "Apache 2.0",
	 * "http://www.apache.org/licenses/LICENSE-2.0", new
	 * ArrayList<VendorExtension>());
	 */

	public static final Contact DEFAULT_CONTACT = new Contact("User Management Support", "", "");
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("User Management API Documentation",
			"User Management Web service Api Documentation", "v1", "", null, "", "", new ArrayList<VendorExtension>());

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json"));

	@Bean
	public Docket api() {

		// Adding Header
		ParameterBuilder globalParameter = new ParameterBuilder();
		globalParameter.name(HttpHeaderConstants.AUTHORIZATION).modelRef(new ModelRef("string")).parameterType("header")
				.required(true).build();
		List<Parameter> headerParams = new ArrayList<Parameter>();
		headerParams.add(globalParameter.build());

		List<ResponseMessage> list = new java.util.ArrayList<>();
		list.add(new ResponseMessageBuilder().code(500).message("Server Error").responseModel(new ModelRef("String"))
				.build());
		list.add(new ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(new ModelRef("String"))
				.build());

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES).globalOperationParameters(headerParams)
				.host("clientshortname.userManagement.com").pathMapping("/").select()
				.apis(RequestHandlerSelectors.basePackage("com.spring.sonu.Microservice.controller")).build();
	}

}
