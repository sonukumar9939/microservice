package com.spring.sonu.Microservice.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface EncryptionConstants {

	public static final String ENCRYPTION_ALOGROTHM = "AES/GCM/NoPadding";
	public static final int TAG_LENGTH_BIT = 128;
	public static final int IV_LENGTH_BYTE = 16;
	public static final int AES_KEY_BIT = 256;
	public static final Charset UTF_8 = StandardCharsets.UTF_8;
	public static final String API_CLIENT_SECRET_KEY = "apiClientSecretKey";
	public static final String API_ALGORITHM_KEY ="apiAlgorithmKey";
	
	
}
