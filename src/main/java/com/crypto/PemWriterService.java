package com.crypto;

import com.nimbusds.jose.jwk.*;
import org.bouncycastle.util.io.pem.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;

public class PemWriterService {
    private static final Logger logger = Logger.getLogger(PemWriterService.class.getName());

    public void writePemFile(RSAKey rsaKey, String pemFilePath) throws PemFileWriteException {
        try {
            // write the RSA public key to a PEM file
            PemObject pemObject = new PemObject("PUBLIC KEY", rsaKey.toRSAPublicKey().getEncoded());
            Path path = Paths.get(pemFilePath);
            try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(Files.newOutputStream(path)))) {
                pemWriter.writeObject(pemObject);
            }

            // read and print the content of the PEM file
            List<String> lines = Files.readAllLines(path);
            String content = String.join("\n", lines);
            logger.log(Level.INFO, content);
        } catch (Exception e) {
            throw new PemFileWriteException("Failed to write PEM file", e);
        }
    }
}
