package com.github.houssemba.mixtwitt.security.parsers;

import com.github.houssemba.mixtwitt.security.encoders.TokenEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class AuthHeaderTokenParser implements TokenParser {

	private final String headerName;
	private final TokenEncoder encoder;

	public AuthHeaderTokenParser(String headerName, TokenEncoder encoder) {
		this.headerName = headerName;
		this.encoder = encoder;
	}

	@Override
	public String parse(HttpServletRequest rq) {
		return Optional.ofNullable(rq.getHeader(headerName))
				.map(encoder::decode)
				.orElse(null);
	}

	@Override
	public void put(HttpServletResponse rsp, String value, boolean rememberMe) {
		rsp.addHeader(headerName, encoder.encode(value));
	}

	@Override
	public void remove(HttpServletResponse rsp) {
		// Nothing to do.
	}
}
