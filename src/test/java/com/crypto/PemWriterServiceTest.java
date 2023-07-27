package com.crypto;

import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PemWriterServiceTest {

    @Test
    void testWritePemFile_InvalidPath() {
        PemWriterService pemWriterService = new PemWriterService();
        RSAKey rsaKey = mock(RSAKey.class);

        assertThrows(PemFileWriteException.class, () -> pemWriterService.writePemFile(rsaKey, "/invalid/path"));
    }
}
