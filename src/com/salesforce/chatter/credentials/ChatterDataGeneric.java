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
package com.salesforce.chatter.credentials;

import com.salesforce.chatter.authentication.ChatterAuthMethod;
import com.salesforce.chatter.authentication.IChatterData;

/**
 * <p>This is a generic (blank) bean to initialize an instance of {@link IChatterData}.</p>
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class ChatterDataGeneric implements IChatterData {

    private final String apiVersion;
    private final String instanceUrl;

    private final ChatterAuthMethod authMethod;
    private final String username;
    private final String password;

    private final String clientKey;
    private final String clientSecret;
	private final String authenticationEnvironment = "https://login.salesforce.com";

    /**
     * 
     * @param apiVersion
     * @param instanceUrl
     * @param authMethod This will be parsed into a {@link ChatterAuthMethod}
     * @param username
     * @param password
     * @param clientKey
     * @param clientSecret
     */
    public ChatterDataGeneric(String apiVersion, String instanceUrl, String authMethod, String username,
        String password, String clientKey, String clientSecret) {
        this.apiVersion = apiVersion;
        this.instanceUrl = instanceUrl;

        this.authMethod = ChatterAuthMethod.valueOf(authMethod.toUpperCase());
        this.username = username;
        this.password = password;

        this.clientKey = clientKey;
        this.clientSecret = clientSecret;
    }
    
    /**
     * 
     * @param apiVersion
     * @param instanceUrl
     * @param authMethod
     * @param username
     * @param password
     * @param clientKey
     * @param clientSecret
     */
    public ChatterDataGeneric(String apiVersion, String instanceUrl, ChatterAuthMethod authMethod, String username,
        String password, String clientKey, String clientSecret) {
        this.apiVersion = apiVersion;
        this.instanceUrl = instanceUrl;

        this.authMethod = authMethod;
        this.username = username;
        this.password = password;

        this.clientKey = clientKey;
        this.clientSecret = clientSecret;
    }

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

    // Unused at the moment
    @Override
    public String getRefreshToken() {
        return null;
    }

    // Unused at the moment
    @Override
    public String getClientCode() {
        return null;
    }

    // Unused at the moment
    @Override
    public String getClientCallback() {
        return null;
    }

	@Override
	public String getEnvironment() {
		return authenticationEnvironment;
	}
}
