package com.crypto;

import com.nimbusds.jose.jwk.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class JwkService {
    private static final Logger logger = Logger.getLogger(JwkService.class.getName());

    public Optional<RSAKey> fetchRsaKey(URL url, String kid) throws JwkFetchException {
        try {
            // fetch the JWK set
            JWKSet jwkSet = JWKSet.load(url);
            List<JWK> jwks = jwkSet.getKeys();
            // find the JWK with the provided kid
            for (JWK jwk : jwks) {
                if (jwk.getKeyID().equals(kid)) {
                    return Optional.of((RSAKey) jwk);
                }
            }

            logger.log(Level.WARNING, "No key found with the provided kid");
            return Optional.empty();
        } catch (Exception e) {
            throw new JwkFetchException("Failed to fetch JWK", e);
        }
    }
}
