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
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.ChatterAuthenticate;
import com.salesforce.chatter.authentication.IChatterData;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;
import com.salesforce.chatter.authentication.methods.AuthentificationMethod;
import com.salesforce.chatter.commands.ChatterCommand;
import com.salesforce.chatter.message.Message;

/**
 * <p>This is the API 3rd party tools talk against.</p>
 * 
 * <p>It acts as a facade into the API, Message transformation and authentication.</p>
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class ChatterService {

    /**
     * <p>The credentials data used to authenticate. Provided when this service is instantiated.</p>
     */
    private final IChatterData chatterData;

    /**
     * <p>Some tools used to generate instance specific URIs or do common tasks.</p>
     */
    private ChatterTools tools;

    /**
     * <p>Used to perform the authentication.</p>
     */
    private ChatterAuthenticate chatterAuthenticator = new ChatterAuthenticate();

    /**
     * <p>A library to manipulate payload (Messages) before sending it through the API.</p>
     */
    private ChatterCommands chatterCommands = new ChatterCommands();

    /**
     * <p>A useful quick way to check if we have a accessToken (see {@link #authToken}).</p>
     */
    private boolean authenticated;

    /**
     * <p>Our access Token used to communicate with the Salesforce.com APIs.</p>
     */
    private ChatterAuthToken authToken;

    /**
     * <p>If set, this is used as the client to use for communication.</p>
     */
    private HttpClient client;

    /**
     * <p>NOTE: Authentication is deferred until communicated with the API is required.</p>
     * 
     * <p>So, even if incorrect credentials are passed here, this won't be apparent
     * until the first API call.</p>
     * 
     * @param chatterData required IChatterData implementation
     */
    public ChatterService(IChatterData chatterData) {
        this.chatterData = chatterData;
        tools = new ChatterTools(chatterData);
    }

    public void setChatterAuthenticate(ChatterAuthenticate chatterAuthenticator) {
        this.chatterAuthenticator = chatterAuthenticator;
    }

    public void setChatterCommands(ChatterCommands chatterCommands) {
        this.chatterCommands = chatterCommands;
    }

    public void setChatterTools(ChatterTools chatterTools) {
        this.tools = chatterTools;
        if (null != client) {
            this.tools.setHttpClient(client);
        }
    }

    /**
     * <p>This allows the caller to override the {@link HttpClient} implementation used during for all Chatter
     * communication.</p>
     * 
     * @param client {@link HttpClient}
     */
    public void setHttpClient(HttpClient client) {
        this.client = client;
        this.tools.setHttpClient(client);
    }

    /**
     * <p>Will send any {@link ChatterCommand} with its associated {@link Message} to the Salesforce API.</p>
     * 
     * <p>This is generally used for commands that support a JSON payload that result in a change to the Org.</p>
     * 
     * <p>It will be POSTed via the Salesforce API.</p>
     * 
     * @param command Any command
     * @param message Any message
     * @throws IOException Thrown if anything goes wrong communicated with the Salesforce.com API.</p>
     * @throws UnauthenticatedSessionException thrown if the credentials are incorrect.</p>
     * @throws AuthenticationException
     */
    public HttpMethod executeCommand(ChatterCommand command, Message message) throws IOException,
        UnauthenticatedSessionException, AuthenticationException {
        if (!authenticated) {
            authenticateSession();
        }

        String uri = tools.getChatterUri(command.getURI());
        String payload = chatterCommands.getJsonPayload(message);

        HttpMethod method = chatterCommands.getJsonPost(uri, payload);
        method = tools.addHeaders(method, authToken);

        tools.executeMethod(method);
        return method;
    }

    /**
     * <p>Sends a query {@link ChatterCommand} to the Salesforce API.</p>
     * 
     * <p>This is done using a GET request.</p>
     * 
     * <p>The return value will contain the status code and return header/body.</p>
     * 
     * <p>The HttpMethod's response body will most likely be JSON.</p>
     * 
     * @param command Any command
     * @return The HttpMethod (GET) that was used during the request.
     * @throws IOException Thrown if anything goes wrong communicated with the Salesforce.com API.</p>
     * @throws UnauthenticatedSessionException thrown if the credentials are incorrect.</p>
     * @throws AuthenticationException
     */
    public HttpMethod executeCommand(ChatterCommand command) throws IOException,
        UnauthenticatedSessionException, AuthenticationException {
        if (!authenticated) {
            authenticateSession();
        }

        String uri = tools.getChatterUri(command.getURI());
        HttpMethod method = new GetMethod(uri);
        method = tools.addHeaders(method, authToken);

        tools.executeMethod(method);
        return method;
    }

    /**
     * @throws IOException Thrown if anything goes wrong communicated with the Salesforce.com API.</p>
     * @throws UnauthenticatedSessionException thrown if the credentials are incorrect.</p>
     * @throws AuthenticationException
     */
    protected void authenticateSession() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        AuthentificationMethod method = chatterAuthenticator.getAuthentificationMethod(chatterData);
        if (null != client) {
            method.setHttpClient(client);
        }
        authToken = method.authenticate();
        authenticated = true;
    }
}
