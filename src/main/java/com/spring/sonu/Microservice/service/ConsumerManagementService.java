package com.spring.sonu.Microservice.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.sonu.Microservice.model.UserDetails;

@Service
public class ConsumerManagementService {

	public String getJSONString(@Valid UserDetails userDetails) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(userDetails);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}
