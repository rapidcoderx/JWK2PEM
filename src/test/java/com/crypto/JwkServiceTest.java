package com.crypto;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JwkServiceTest {

    @Test
    void testFetchRsaKey_InvalidUrl() {
        JwkService jwkService = new JwkService();

        assertThrows(JwkFetchException.class, () -> jwkService.fetchRsaKey(new URL("http://invalidurl"), "kid"));
    }
}
