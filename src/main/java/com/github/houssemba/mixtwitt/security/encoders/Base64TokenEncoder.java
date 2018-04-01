package com.github.houssemba.mixtwitt.security.encoders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64TokenEncoder implements TokenEncoder {

	@Override
	public String encode(String decodedValue) {
		return Base64.getEncoder().encodeToString(decodedValue.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String decode(String encodedValue) {
		return new String(Base64.getDecoder().decode(encodedValue), StandardCharsets.UTF_8);
	}
}
