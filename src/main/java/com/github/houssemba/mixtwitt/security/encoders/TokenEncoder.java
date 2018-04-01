package com.github.houssemba.mixtwitt.security.encoders;

public interface TokenEncoder {

	String encode(String decodedValue);

	String decode(String encodedValue);
}
