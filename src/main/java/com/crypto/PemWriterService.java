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
            try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(pemFilePath))))) {
                pemWriter.writeObject(pemObject);
            }

            // read and print the content of the PEM file
            List<String> lines = Files.readAllLines(Paths.get(pemFilePath));
            lines.forEach(line -> logger.log(Level.INFO, line));
        } catch (Exception e) {
            throw new PemFileWriteException("Failed to write PEM file", e);
        }
    }
}
