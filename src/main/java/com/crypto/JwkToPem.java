package com.crypto;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

public class JwkToPem {
    public static void main(String[] args) throws Exception {
        // the key ID from an environment variable or default value
        String kid = System.getenv("KID") != null ? System.getenv("KID") : "defaultKid";

        // the Okta domain from an environment variable or default value
        String oktaDomain = System.getenv("OKTA_DOMAIN") != null ? System.getenv("OKTA_DOMAIN") : "https://defaultOktaDomain";

        // the endpoint from an environment variable or default value
        String endpoint = System.getenv("ENDPOINT") != null ? System.getenv("ENDPOINT") : "/oauth2/default/v1/keys";

        // the full URI
        URL url = new URL(oktaDomain + endpoint);

        // the output PEM file
        Path pemFilePath = Paths.get("public_key.pem");

        // if the PEM file already exists, delete it
        if (Files.exists(pemFilePath)) {
            Files.delete(pemFilePath);
        }

        // fetch the JWK set
        JWKSet jwkSet = JWKSet.load(url);
        List<JWK> jwks = jwkSet.getKeys();

        // find the JWK with the provided kid
        RSAKey rsaKey = null;
        for (JWK jwk : jwks) {
            if (jwk.getKeyID().equals(kid)) {
                rsaKey = (RSAKey) jwk;
                break;
            }
        }

        if (rsaKey == null) {
            System.out.println("No key found with the provided kid");
            return;
        }

        // convert the JWK to an RSA public key
        RSAPublicKey publicKey = rsaKey.toRSAPublicKey();

        // write the RSA public key to a PEM file
        PemObject pemObject = new PemObject("PUBLIC KEY", publicKey.getEncoded());
        try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(Files.newOutputStream(pemFilePath)))) {
            pemWriter.writeObject(pemObject);
        }

        // read and print the content of the PEM file
        List<String> lines = Files.readAllLines(pemFilePath);
        lines.forEach(System.out::println);
    }
}
