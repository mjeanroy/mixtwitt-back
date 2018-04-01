package com.github.houssemba.mixtwitt.security.parsers;

import com.github.houssemba.mixtwitt.security.encoders.TokenEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieTokenParser implements TokenParser {

	private final String cookieName;
	private final TokenEncoder encoder;

	public CookieTokenParser(String cookieName, TokenEncoder encoder) {
		this.cookieName = cookieName;
		this.encoder = encoder;
	}

	@Override
	public String parse(HttpServletRequest rq) {
		Cookie[] cookies = rq.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}

		return Arrays.stream(cookies)
				.filter(c -> c.getName().equals(cookieName))
				.map(Cookie::getValue)
				.map(encoder::decode)
				.findFirst().orElse(null);
	}

	@Override
	public void put(HttpServletResponse rsp, String value, boolean rememberMe) {
		Cookie cookie = new Cookie(cookieName, encoder.encode(value));
		cookie.setMaxAge(rememberMe ? Integer.MAX_VALUE : -1);
		putCookie(rsp, cookie);
	}

	@Override
	public void remove(HttpServletResponse rsp) {
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		putCookie(rsp, cookie);
	}

	private void putCookie(HttpServletResponse rsp, Cookie cookie) {
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		rsp.addCookie(cookie);
	}
}
