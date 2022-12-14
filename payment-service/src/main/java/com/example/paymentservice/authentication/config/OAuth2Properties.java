package com.example.paymentservice.authentication.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OAuth2 properties define properties for OAuth2-based microservices.
 */
@Component
public class OAuth2Properties {
	private final WebClientConfiguration webClientConfiguration = new WebClientConfiguration();

	private final SignatureVerification signatureVerification = new SignatureVerification();

	public WebClientConfiguration getWebClientConfiguration() {
		return webClientConfiguration;
	}

	public SignatureVerification getSignatureVerification() {
		return signatureVerification;
	}

	public static class WebClientConfiguration {

		@Value("${oauth2.oauth.clientId}")
		private String clientId;

		@Value("${oauth2.oauth.secret}")
		private String secret;
		/**
		 * Holds the session timeout in seconds for non-remember-me sessions.
		 * After so many seconds of inactivity, the session will be terminated.
		 * Only checked during token refresh, so long access token validity may
		 * delay the session timeout accordingly.
		 */
		private final int sessionTimeoutInSeconds = 1800;
		/**
		 * Defines the cookie domain. If specified, cookies will be set on this domain.
		 * If not configured, then cookies will be set on the top-level domain of the
		 * request you sent, i.e. if you send a request to <code>app1.your-domain.com</code>,
		 * then cookies will be set <code>on .your-domain.com</code>, such that they
		 * are also valid for <code>app2.your-domain.com</code>.
		 */
		private String cookieDomain;

	}

	@Data
	public static class SignatureVerification {
		/**
		 * Maximum refresh rate for public keys in ms.
		 * We won't fetch new public keys any faster than that to avoid spamming UAA in case
		 * we receive a lot of "illegal" tokens.
		 */
		private long publicKeyRefreshRateLimit = 10 * 1000L;
		/**
		 * Maximum TTL for the public key in ms.
		 * The public key will be fetched again from UAA if it gets older than that.
		 * That way, we make sure that we get the newest keys always in case they are updated there.
		 */
		private long ttl = 24 * 60 * 60 * 1000L;
		/**
		 * Endpoint where to retrieve the public key used to verify token signatures.
		 */
		@Value("${oauth2.oauth.publicKeyEndpointUri}")
		private String publicKeyEndpointUri;
	}
}

