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
package com.salesforce.chatter.authentication;

import java.io.IOException;

import com.salesforce.chatter.authentication.methods.AuthentificationMethod;
import com.salesforce.chatter.authentication.methods.ClientSecretAuthenication;
import com.salesforce.chatter.authentication.methods.InteractiveAuthentication;
import com.salesforce.chatter.authentication.methods.RefreshTokenAuthentication;
import com.salesforce.chatter.authentication.methods.UsernamePasswordAuthentication;

/**
 * <p>ChatterAuthentice is where all authentication is done. It takes various input credentials and uses those to
 * request an Access Token from Salesforce.com.</p>
 * 
 * <p>This token is then returned to the caller and can be used in future calls to the Salesforce.com app.</p>
 * 
 * @see http://wiki.developerforce.com/index.php/Digging_Deeper_into_OAuth_2.0_on_Force.com)
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class ChatterAuthenticate {

    /**
     * <p>Uses the information in {@link IChatterData} to figure out when authentication type to use. If successful, it
     * returns a {@link ChatterAuthToken} which can be used to perform various queries/action against the Salesforce.com
     * app.</p>
     * 
     * @param chatterData
     * @return {@link ChatterAuthToken} if successful, otherwise the {@link UnauthenticatedSessionException} is thrown
     * @throws IOException thrown if the communication between client and server fails
     * @throws UnauthenticatedSessionException thrown if the credentials are invalid or another exception occurred
     * @throws AuthenticationException
     */
    public ChatterAuthToken authenticate(IChatterData chatterData) throws IOException, UnauthenticatedSessionException,
        AuthenticationException {
        return getAuthentificationMethod(chatterData).authenticate();
    }

    public AuthentificationMethod getAuthentificationMethod(IChatterData chatterData) throws AuthenticationException {
        switch (chatterData.getAuthMethod()) {
        case PASSWORD:
            return new UsernamePasswordAuthentication(chatterData);
        case INTERACTIVE:
            return new InteractiveAuthentication(chatterData);
        case CLIENT_SECRET:
            return new ClientSecretAuthenication(chatterData, null);
        case REFRESH_TOKEN:
            return new RefreshTokenAuthentication(chatterData);
        default:
            throw new AuthenticationException(chatterData.getAuthMethod()
                + " is an unrecognised Auth form.");
        }
    }
}
