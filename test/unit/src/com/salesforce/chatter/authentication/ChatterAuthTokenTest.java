/*******************************************************************************
 * Copyright (c) 2013, salesforce.com, inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 * 
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 * 
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package com.salesforce.chatter.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.junit.Test;

import com.salesforce.chatter.authentication.methods.AuthentificationMethod;

public class ChatterAuthTokenTest {

    /**
     * <p>This is copied straight out of {@link AuthentificationMethod}.</p>
     */
    private final static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    static {
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    @Test
    public void testNullToken() {
        ChatterAuthToken token = new ChatterAuthToken(null);
        assertNull(token.getAccessToken());
    }

    @Test
    public void testToken() {
        String input = "TEST";
        ChatterAuthToken token = new ChatterAuthToken(input);
        assertEquals(input, token.getAccessToken());
    }

    @Test
    public void testJsonAndGetters() throws IOException {
        ChatterAuthToken token = mapper.readValue(getTestResponse(), ChatterAuthToken.class);
        assertEquals("https://login.salesforce.com/id/ID", token.getId());
        assertEquals("1390419152257", token.getIssuedAt());
        assertEquals("scope", token.getScope());
        assertEquals("refresh_token", token.getRefreshToken());
        assertEquals("https://naX.salesforce.com", token.getInstanceUrl());
        assertEquals("signature", token.getSignature());

    }

    private String getTestResponse() {
        return "{\"id\":\"https://login.salesforce.com/id/ID\",\"issued_at\":\"1390419152257\",\"instance_url\":\"https://naX.salesforce.com\",\"signature\":\"signature\",\"access_token\":\"access_token\",\"scope\":\"scope\",\"refresh_token\":\"refresh_token\"}";
    }
}
