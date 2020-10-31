package com.spring.sonu.Microservice.service;

import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.sonu.Microservice.constants.CommonConstants;
import com.spring.sonu.Microservice.constants.EncryptionConstants;
import com.spring.sonu.Microservice.constants.OperationType;
import com.spring.sonu.Microservice.model.UserDetails;

@Service
public class ConsumerManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerManagementService.class);

	public String getJSONString(UserDetails userDetails) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(userDetails);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	public JSONObject encryptPayload(String json, String secretkey, String salt) throws Exception {
		JSONObject response = new JSONObject();
		try {
			byte[] iv = new byte[16];
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretkey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			String encryptedData = Base64.getEncoder().encodeToString(cipher.doFinal(json.getBytes("UTF-8")));
			response.put(CommonConstants.STATUS, CommonConstants.STATUS_SUCCESS);
			response.put(CommonConstants.ENCRYPTED_DATA, encryptedData);
			return response;
		} catch (Exception exception) {
			LOGGER.error("Error Occured while encrypting Payload");
			response.put(CommonConstants.STATUS, CommonConstants.STATUS_FAILED);
			response.put(CommonConstants.message, exception.getMessage());
		}
		return response;
	}

	public String decryptPayload(String encryptredData, String secretkey, String salt) throws Exception {

		try {
			byte[] iv = new byte[16];
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretkey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encryptredData)));
		} catch (Exception exception) {
			LOGGER.error("Error occured while Decryption" + exception.getMessage());
		}
		return "";

	}

	public ResponseEntity<String> invokeUserInfoApi(String apiClientSecretKey, String apiAlgorithmKey,
			HttpServletRequest request, String encryptedPayload, String apiPath, String operationType, String fileType) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(request.getScheme())
				.host(request.getServerName()).port(request.getServerPort()).path(apiPath).build();
		LOGGER.info("Request Url : " + uriComponents.toString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set(EncryptionConstants.API_CLIENT_SECRET_KEY,apiClientSecretKey);
		headers.set(EncryptionConstants.API_ALGORITHM_KEY,apiAlgorithmKey);
		headers.set(CommonConstants.FILE_TYPE, fileType);
		
		HttpEntity<String> httpEntity = new HttpEntity<>(encryptedPayload, headers);
		ResponseEntity<String> response = null;
		if(OperationType.POST.toString().equalsIgnoreCase(operationType)) {
			
		}
		return null;
	}

}
