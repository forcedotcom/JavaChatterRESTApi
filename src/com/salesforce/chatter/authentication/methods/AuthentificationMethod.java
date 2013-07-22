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

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;

public abstract class AuthentificationMethod {

    /**
     * <p>The verification URL is used in interactive authentication, where the user copies and pastes the response code
     * to us.</p>
     */
    public final static String VERIFICATION_URL = "https://login.salesforce.com/services/oauth2/authorize?response_type=code&client_id=__CLIENTID__&redirect_uri=__REDIRECTURI__";

    /**
     * <p>This URL is where the default Salesforce.com authentication is performed (regardless of environment).</p>
     */
    public final static String ENVIRONMENT = "https://login.salesforce.com/services/oauth2/token";

	private final static ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	static {
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
	}

    private HttpClient client = new HttpClient();

    public abstract ChatterAuthToken authenticate() throws IOException, UnauthenticatedSessionException,
        AuthenticationException;

    public void setHttpClient(HttpClient client) {
        this.client = client;
    }

    public HttpClient getHttpClient() {
        return client;
    }

    // @formatter:off
    /**
     * <p>Example (good) response:
     * <code><pre>
     * &lt;?xml version="1.0" encoding="UTF-8"&gt;
     * &lt;OAuth&gt;
     * &lt;id&gt;https://login.salesforce.com/id/00DD0000000CABpMAO/005D0000001a2ZsIBF&lt;/id&gt;
     * &lt;issued_at&gt;1313398030985&lt;/issued_at&gt;
     * &lt;refresh_token&gt;5Aep8615VRsd_GrUz0MQ0lwjJAOW1HBOkijtjyWK50U9Xvpelb3vpTeb1GbfDIKixni8SYRz7MpAA==&lt;/refresh_token&gt;
     * &lt;instance_url&gt;https://eu1.salesforce.com&lt;/instance_url&gt;
     * &lt;signature&gt;y3kOwm+XVIAsT/YD6/MLMRoK97FpUWViZnIRDVJwJAA=&lt;/signature&gt;
     * &lt;access_token&gt;00DD0000000CSWp!AR4AQLo8XRX3cJC.8
     * lLH9LZ2EwQ_zlEB5KniKsKQY_bbH6QgaCtx3dU0GY_UjnsOPmUzeAM7eNLqGTRp4MCu60WLMmLbCaAA&lt;/access_token&gt;
     * &lt;/OAuth&gt;
     * </pre></code>
     * </p>
     * 
     * @param response The body of the Salesforce.com API. A JSON string is expected, and the "access_token" is
     *        extracted and used for the {@link ChatterAuthToken}
     * @return A {@link ChatterAuthToken} is successful, or null if the response does not correspond to an access token
     * @throws AuthenticationException This is a wrapped exception, and will most likely contain either 
     *         {@link JsonParseException}, {@link JsonMappingException} or {@link IOException} as its cause
     */
    // @formatter:on
    protected ChatterAuthToken processResponse(String response) throws AuthenticationException {
        try {
        	return mapper.readValue(response, ChatterAuthToken.class);
        } catch (JsonParseException e) {
            throw new AuthenticationException(e);
		} catch (JsonMappingException e) {
            throw new AuthenticationException(e);
		} catch (IOException e) {
            throw new AuthenticationException(e);
		}
    }

    /**
     * <p>Verifies the string looks like a clientCode.</p>
     * 
     * <p>No validation is being done, only the format is checked.</p>
     * 
     * @param code The String to check
     * @return true if it looks like a verification code, false otherwise
     */
    protected boolean isVerificationCode(String code) {
        if (code == null || code.equals(""))
            return false;
        if (code.length() != 76)
            return false;
        if (!code.endsWith("=="))
            return false;
        return true;
    }
}
