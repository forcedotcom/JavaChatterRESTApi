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
 * <p>Currently it assumes the PASSWORD ChatterAuthMethod, but this can be substituted for any valid method,
 * as long as it contains valid credentials.</p>
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class ChatterData implements IChatterData {

    private final String apiVersion = "24.0";
    private final String instanceUrl = "https://naX.salesforce.com";

    private final ChatterAuthMethod authMethod = ChatterAuthMethod.PASSWORD;
    private final String username = "some_user@some_org.com";
    private final String password = "yourPassword";

    private final String clientKey = "TheClientKey";
    private final String clientSecret = "The_numeric_CLientSecret";
	private final String environment = "PRODUCTION";

    @Override
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public String getInstanceUrl() {
        return instanceUrl;
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
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public ChatterAuthMethod getAuthMethod() {
        return authMethod;
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
    public String getClientCallback() {
        return null;
    }

	@Override
	public String getEnvironment() {
		return environment;
	}
}
