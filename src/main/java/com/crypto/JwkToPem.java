package com.crypto;

import com.nimbusds.jose.jwk.*;
import java.net.*;
import java.util.logging.*;

public class JwkToPem {
    private static final Logger logger = Logger.getLogger(JwkToPem.class.getName());

    private final JwkService jwkService;
    private final PemWriterService pemWriterService;

    public JwkToPem(JwkService jwkService, PemWriterService pemWriterService) {
        this.jwkService = jwkService;
        this.pemWriterService = pemWriterService;
    }

    public void run(String kid, String oktaDomain, String endpoint, String pemFilePath) throws JwkToPemException {
        try {
            // the full URI
            URL url = new URL(oktaDomain + endpoint);

            // fetch the RSA key
            RSAKey rsaKey = jwkService.fetchRsaKey(url, kid).orElse(null);
            if (rsaKey == null) {
                logger.log(Level.WARNING, "No key found with the provided kid");
                return;
            }

            // write the RSA key to a PEM file
            pemWriterService.writePemFile(rsaKey, pemFilePath);
        } catch (Exception e) {
            throw new JwkToPemException("Failed to fetch JWK and write PEM file", e);
        }
    }

    public static void main(String[] args) throws Exception {
        // the key ID from an environment variable or default value
        String kid = System.getenv("KID") != null ? System.getenv("KID") : "defaultKid";

        // the Okta domain from an environment variable or default value
        String oktaDomain = System.getenv("OKTA_DOMAIN") != null ? System.getenv("OKTA_DOMAIN") : "https://defaultOktaDomain";

        // the endpoint from an environment variable or default value
        String endpoint = System.getenv("ENDPOINT") != null ? System.getenv("ENDPOINT") : "/oauth2/default/v1/keys";

        // the output PEM file
        String pemFilePath = "public_key.pem";

        JwkToPem jwkToPem = new JwkToPem(new JwkService(), new PemWriterService());
        jwkToPem.run(kid, oktaDomain, endpoint, pemFilePath);
    }
}
