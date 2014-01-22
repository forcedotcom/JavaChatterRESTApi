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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.IChatterData;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;

public class ClientSecretTest {

    @Test
    public void testNormalFlow() throws HttpException, IOException, UnauthenticatedSessionException,
        AuthenticationException {
        IChatterData data = getMockedChatterData();

        ClientSecretAuthentication auth = new ClientSecretAuthentication(data, "");

        HttpClient mockHttpClient = mock(HttpClient.class);
        when(mockHttpClient.executeMethod(any(PostMethod.class))).thenAnswer(
            new ExecuteMethodAnswer("{\"access_token\" : \"abc\" }"));

        auth.setHttpClient(mockHttpClient);

        ChatterAuthToken token = auth.authenticate();
        assertEquals("abc", token.getAccessToken());
    }

    @Test(expected = UnauthenticatedSessionException.class)
    public void testBadResponseCode() throws HttpException, IOException, UnauthenticatedSessionException,
        AuthenticationException {
        IChatterData data = getMockedChatterData();

        ClientSecretAuthentication auth = new ClientSecretAuthentication(data, null);

        HttpClient mockHttpClient = mock(HttpClient.class);
        when(mockHttpClient.executeMethod(any(PostMethod.class))).thenAnswer(
            new ExecuteMethodAnswer("400 Bad Request", HttpStatus.SC_BAD_REQUEST));

        auth.setHttpClient(mockHttpClient);

        auth.authenticate();
        fail();
    }

    private IChatterData getMockedChatterData() {
        IChatterData data = mock(IChatterData.class);
        when(data.getClientKey()).thenReturn("");
        when(data.getClientCode()).thenReturn("");
        when(data.getClientSecret()).thenReturn("");
        when(data.getClientCallback()).thenReturn("");

        return data;
    }

    private class ExecuteMethodAnswer implements Answer<Integer> {

        private final String response;
        private final int responseCode;

        public ExecuteMethodAnswer(String response) {
            this(response, HttpStatus.SC_OK);
        }

        public ExecuteMethodAnswer(String response, int responseCode) {
            this.response = response;
            this.responseCode = responseCode;
        }

        private void setResponseStream(HttpMethodBase httpMethod, InputStream inputStream) throws NoSuchFieldException,
            IllegalAccessException {
            Field privateResponseStream = HttpMethodBase.class.getDeclaredField("responseStream");
            privateResponseStream.setAccessible(true);
            privateResponseStream.set(httpMethod, inputStream);
        }

        public Integer answer(InvocationOnMock invocation) throws FileNotFoundException,
            NoSuchFieldException, IllegalAccessException {
            HttpMethodBase postMethod = (PostMethod) invocation.getArguments()[0];
            setResponseStream(postMethod, IOUtils.toInputStream(response));
            return responseCode;
        }
    }
}
