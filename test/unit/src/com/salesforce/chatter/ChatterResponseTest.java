/*******************************************************************************
 * Copyright (c) 2014, salesforce.com, inc.
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
package com.salesforce.chatter;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.StatusLine;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatterResponseTest {

    @Test
    public void testGetStatusCode() {
        int statusCode = 199;
        HttpMethod method = mock(HttpMethod.class);
        when(method.getStatusCode()).thenReturn(statusCode);

        ChatterResponse response = new ChatterResponse(method);
        assertEquals(statusCode, response.getStatusCode());
    }

    @Test
    public void testGetStatusText() {
        String statusText = "Status Text";
        HttpMethod method = mock(HttpMethod.class);
        when(method.getStatusText()).thenReturn(statusText);

        ChatterResponse response = new ChatterResponse(method);
        assertEquals(statusText, response.getStatusText());
    }

    @Test
    public void testGetStatusLine() throws HttpException {
        String statusLineString = "HTTP/1.0 199 Status Text";
        HttpMethod method = mock(HttpMethod.class);
        StatusLine statusLine = new StatusLine(statusLineString);
        when(method.getStatusLine()).thenReturn(statusLine);

        ChatterResponse response = new ChatterResponse(method);
        assertEquals(statusLineString, response.getStatusLine());
    }

    @Test
    public void testGetStatusLineWithANullpointerException() throws HttpException {
        HttpMethod method = mock(HttpMethod.class);
        when(method.getStatusLine()).thenThrow(new NullPointerException());

        ChatterResponse response = new ChatterResponse(method);
        String statusLine = response.getStatusLine();
        assertNull(statusLine);
    }

    @Test
    public void testGetResponseBodyAsString() throws IOException {
        String responseText = "{ some JSON for example }";
        HttpMethod method = mock(HttpMethod.class);
        when(method.getResponseBodyAsString()).thenReturn(responseText);

        ChatterResponse response = new ChatterResponse(method);
        assertEquals(responseText, response.getResponseBodyAsString());
    }
}
