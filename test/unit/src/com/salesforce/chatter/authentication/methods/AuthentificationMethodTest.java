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
package com.salesforce.chatter.authentication.methods;

import org.junit.Test;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.ChatterAuthToken;

import static org.junit.Assert.*;

public class AuthentificationMethodTest {

    @Test (expected=AuthenticationException.class)
    public void testInvalidProcessResponse() throws AuthenticationException {
        AuthentificationMethod auth = new UsernamePasswordAuthentication(null);

        String response = "400 Bad Request";
        auth.processResponse(response);
        fail("We should have gotten a AuthenticationException by now");
    }

    @Test
    public void testProcessResponse() throws AuthenticationException {
        AuthentificationMethod auth = new UsernamePasswordAuthentication(null);

        String response = "{ \"access_token\" : \"test\" }";
        ChatterAuthToken token = auth.processResponse(response);

        assertEquals("test", token.getAccessToken());
    }

    @Test
    public void testVerificationCode() {
        AuthentificationMethod auth = new UsernamePasswordAuthentication(null);

        boolean result;

        String nullCode = null;
        result = auth.isVerificationCode(nullCode);
        assertFalse(result);

        String emptyCode = "";
        result = auth.isVerificationCode(emptyCode);
        assertFalse(result);

        String wrongLenghCode = "123456789";
        result = auth.isVerificationCode(wrongLenghCode);
        assertFalse(result);

        String NoDoubleEqualSignsCode = "aPrxb0v8x4SoqfmZmKMRPKgxoKDEL5d5ZdPaYVxdpVSywTCIFBw_cfpyNB3Whit5GYWZhwzI7QXX";
        result = auth.isVerificationCode(NoDoubleEqualSignsCode);
        assertFalse(result);

        String validVerificationCode = "aPrxb0v8x4SoqfmZmKMRPKgxoKDEL5d5ZdPaYVxdpVSywTCIFBw_cfpyNB3Whit5GYWZhwzI7Q==";

        result = auth.isVerificationCode(validVerificationCode);
        assertTrue(result);
    }
}
