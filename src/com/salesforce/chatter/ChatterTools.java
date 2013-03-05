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
package com.salesforce.chatter;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;

import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.IChatterData;

public class ChatterTools {
    private final IChatterData chatterData;
    private HttpClient httpClient = new HttpClient();

    public ChatterTools(IChatterData chatterData) {
        this.chatterData = chatterData;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /* Private helper methods */
    private String getServiceUri() {
        return "/services/data/v" + chatterData.getApiVersion();
    }

    public String getChatterUri(String command) {
        return chatterData.getInstanceUrl() + getServiceUri() + command;
    }

    public HttpMethod addHeaders(HttpMethod method, ChatterAuthToken token) {
        method.setRequestHeader("Authorization", "OAuth " + token.getAccessToken());
        method.addRequestHeader("X-PrettyPrint", "1");

        return method;
    }

    /**
     * <p>Proxy method for {@link ChatterTools#execute(HttpMethod)}.</p>
     * <p>Will use a default {@link HttpClient} to execute the {@link HttpMethod} provided.</p>
     * 
     * <p>TODO: Probably needs proxy handling.</p>
     * 
     * @see ChatterTools#execute(HttpMethod)
     * @param method
     * @throws HttpException
     * @throws IOException
     */
    public void executeMethod(HttpMethod method) throws HttpException, IOException {
        httpClient.executeMethod(method);
    }
}
