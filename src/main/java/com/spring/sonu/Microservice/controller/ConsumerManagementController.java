package com.spring.sonu.Microservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.sonu.Microservice.constants.CommonConstants;
import com.spring.sonu.Microservice.constants.HttpStatusMessageConstants;
import com.spring.sonu.Microservice.constants.OperationType;
import com.spring.sonu.Microservice.exception.ExceptionResponse;
import com.spring.sonu.Microservice.model.UserDetails;
import com.spring.sonu.Microservice.response.UserManagementResponse;
import com.spring.sonu.Microservice.service.ConsumerManagementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = CommonConstants.USER_MANAGEMENT_REQUEST_PATH)
@Api(value = "ConsumerManagement", description = "REST API to Process User Info", tags = { "User Management" })
public class ConsumerManagementController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerManagementController.class);
	
	private static final String USER_INFO_SAVE_API = "/spring/smartapp/user/save";
	
	@Value("${user.api.client.secret.key}")
	private String apiClientSecretKey;

	@Value("${user.api.alogorithm.key}")
	private String apiAlgorithmKey;
	
	@Autowired
	private ConsumerManagementService consumerManagementService;

	@ApiOperation(value = "Save User Info")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = HttpStatusMessageConstants._201_MESSAGE, response = UserManagementResponse.class),
			@ApiResponse(code = 400, message = HttpStatusMessageConstants._400_MESSAGE, response = ExceptionResponse.class),
			@ApiResponse(code = 204, message = HttpStatusMessageConstants._401_MESSAGE, response = ExceptionResponse.class),
			@ApiResponse(code = 500, message = HttpStatusMessageConstants._500_MESSAGE, response = ExceptionResponse.class)
	})
	@RequestMapping(method = RequestMethod.POST, value = "/userInfo/save", produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<String> saveUserInfo(@RequestParam String fileType,
			@Valid @RequestBody UserDetails userDetails, @RequestHeader HttpHeaders headers, HttpServletRequest request)
			throws Exception {

		LOGGER.info("User Info Save Process Initiated");
		String json = consumerManagementService.getJSONString(userDetails);
		JSONObject encryptedPayload = consumerManagementService.encryptPayload(json, this.apiClientSecretKey,this.apiAlgorithmKey);
		ResponseEntity<String> response = consumerManagementService.invokeUserInfoApi(this.apiClientSecretKey,
				this.apiAlgorithmKey, request, encryptedPayload.getString(CommonConstants.ENCRYPTED_DATA),
				USER_INFO_SAVE_API, OperationType.POST.toString(),fileType);
		// String decryptedData =
		// consumerManagementService.decryptPayload(encryptredData,this.apiClientSecretKey,this.apiAlgorithmKey);
		// String decryptedData ="";
		// return new ResponseEntity<String>(decryptedData,HttpStatus.OK);
		return response;
	}

}
