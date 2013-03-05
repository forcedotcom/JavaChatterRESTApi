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

import com.salesforce.chatter.authentication.ChatterAuthMethod;
import com.salesforce.chatter.authentication.IChatterData;

/**
 * <p>These credentials should be substituted for valid data for the integration tests to succeed.</p>
 * 
 * <p>It assumes the INTERACTIVE ChatterAuthMethod, since it is used by the "testPostInteractiveLogin" test on
 * {@link TestChatterPost}.</p>
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class ChatterAuthInteractive implements IChatterData {

    private final String apiVersion = "24.0";
    private final String instanceUrl = "https://naX.salesforce.com";

    private final ChatterAuthMethod authMethod = ChatterAuthMethod.INTERACTIVE;
    private final String clientKey = "3MVG9yZ.WNe6byQDS1oBDJg6vP82qy7w.OVregoIATuJtBxxIxDQmbG9nw8masqSUAsCED6CCNx.3zaWScqph";
    private final String clientSecret = "6830641966138142806";
    private final String clientCallback = "oob";

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public String getInstanceUrl() {
        return instanceUrl;
    }

    @Override
    public ChatterAuthMethod getAuthMethod() {
        return authMethod;
    }

    @Override
    public String getClientCallback() {
        return clientCallback;
    }

    @Override
    public String getClientKey() {
        return clientKey;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getRefreshToken() {
        return null;
    }

    @Override
    public String getClientCode() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
