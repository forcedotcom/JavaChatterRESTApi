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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.IChatterData;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(InteractiveAuthentication.class)
public class InteractiveAuthenticationTest {

    @Test
    public void testNormalFlow() throws Exception {
        InputStream orig = System.in;
        try {
            IChatterData data = getMockedChatterData();

            String validVerificationCode = "aPrxb0v8x4SoqfmZmKMRPKgxoKDEL5d5ZdPaYVxdpVSywTCIFBw_cfpyNB3Whit5GYWZhwzI7Q==";
            InputStream in = IOUtils.toInputStream(validVerificationCode);

            ClientSecretAuthentication auth2 = mock(ClientSecretAuthentication.class);
            when(auth2.authenticate()).thenReturn(new ChatterAuthToken("abc"));
            PowerMockito.whenNew(ClientSecretAuthentication.class).withArguments(any(), anyString()).thenReturn(auth2);

            System.setIn(in);

            InteractiveAuthentication auth = new InteractiveAuthentication(data);
            ChatterAuthToken token = auth.authenticate();
            assertEquals("abc", token.getAccessToken());
        } finally {
            System.setIn(orig);
        }
    }

    @Test(expected = UnauthenticatedSessionException.class)
    public void testBadResponseCode() throws Exception {
        InputStream orig = System.in;
        try {
            IChatterData data = getMockedChatterData();

            String invalidVerificationCode = "BAD";
            InputStream in = IOUtils.toInputStream(invalidVerificationCode);

            System.setIn(in);

            InteractiveAuthentication auth = new InteractiveAuthentication(data);
            auth.authenticate();

        } finally {
            System.setIn(orig);
        }
    }

    private IChatterData getMockedChatterData() {
        IChatterData data = mock(IChatterData.class);
        when(data.getClientKey()).thenReturn("");
        when(data.getClientCallback()).thenReturn("");

        return data;
    }
}
