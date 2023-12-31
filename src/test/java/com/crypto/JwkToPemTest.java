package com.crypto;

import com.nimbusds.jose.jwk.RSAKey;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class JwkToPemTest {

    @Test
    void testRun_ValidInputs() throws Exception {
        // Create mocks
        JwkService mockJwkService = Mockito.mock(JwkService.class);
        PemWriterService mockPemWriterService = Mockito.mock(PemWriterService.class);

        // Set up mock behavior
        RSAKey mockRsaKey = Mockito.mock(RSAKey.class);
        when(mockJwkService.fetchRsaKey(any(URL.class), anyString())).thenReturn(Optional.of(mockRsaKey));

        // Create instance to test
        JwkToPem jwkToPem = new JwkToPem(mockJwkService, mockPemWriterService);

        // Run the method under test
        assertDoesNotThrow(() -> jwkToPem.run("kid", "https://oktaDomain", "/endpoint", "public_key.pem"));

        // Verify interactions
        verify(mockJwkService).fetchRsaKey(any(URL.class), anyString());
        verify(mockPemWriterService).writePemFile(any(RSAKey.class), anyString());
    }

    @Test
    void testRun_NoKeyFound() throws Exception {
        // Create mocks
        JwkService mockJwkService = Mockito.mock(JwkService.class);
        PemWriterService mockPemWriterService = Mockito.mock(PemWriterService.class);

        // Set up mock behavior
        when(mockJwkService.fetchRsaKey(any(URL.class), anyString())).thenReturn(Optional.empty());

        // Create instance to test
        JwkToPem jwkToPem = new JwkToPem(mockJwkService, mockPemWriterService);

        // Run the method under test
        assertDoesNotThrow(() -> jwkToPem.run("kid", "https://oktaDomain", "/endpoint", "public_key.pem"));

        // Verify interactions
        verify(mockJwkService).fetchRsaKey(any(URL.class), anyString());
        verifyNoInteractions(mockPemWriterService);
    }
    @Test
    void testRun() throws Exception {
        // Create mocks
        JwkService mockJwkService = Mockito.mock(JwkService.class);
        PemWriterService mockPemWriterService = Mockito.mock(PemWriterService.class);

        // Set up mock behavior
        RSAKey mockRsaKey = Mockito.mock(RSAKey.class);
        when(mockJwkService.fetchRsaKey(Mockito.any(URL.class), Mockito.anyString()))
                .thenReturn(Optional.of(mockRsaKey));

        // Create instance to test
        JwkToPem jwkToPem = new JwkToPem(mockJwkService, mockPemWriterService);

        // Run the method under test
        assertDoesNotThrow(() -> jwkToPem.run("kid", "https://oktaDomain", "/endpoint", "public_key.pem"));

        // Verify interactions
        Mockito.verify(mockJwkService).fetchRsaKey(Mockito.any(URL.class), Mockito.anyString());
        Mockito.verify(mockPemWriterService).writePemFile(Mockito.any(RSAKey.class), Mockito.anyString());
    }
}
