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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.IChatterData;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;

/**
 * <p>Asks the user to get the verificationCode (clientSecret) and use {@link ClientSecretAuthentication} to complete the
 * authentication.</p>
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class InteractiveAuthentication extends AuthentificationMethod {

    private int attempts = 3;

    private final IChatterData chatterData;

    public InteractiveAuthentication(IChatterData chatterData) {
        this.chatterData = chatterData;
    }

    /**
     * <p>Start the interactive authentication. It will ask the user to visit the authentication page and report back
     * the verification code.</p>
     * 
     * @return ChatterAuthToken When authenticated with success, the ChatterAuthToken will be returned
     * @throws IOException Thrown when a problem during I/O occurs
     * @throws AuthenticationException in case we got a String that looked like a Verification code, but was the wrong
     *         one
     * @throws UnauthenticatedSessionException Thrown if after several attempts no valid authentication token was
     *         created
     */
    public ChatterAuthToken authenticate() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        boolean keepTrying = true;
        while (keepTrying) {
            // Request this from the user...
            System.out.println("Please visit the following URL and report back the verification code...");
            String uri = VERIFICATION_URL;
            uri = uri.replace("__CLIENTID__", chatterData.getClientKey());
            uri = uri.replace("__REDIRECTURI__", chatterData.getClientCallback());

            System.out.println(uri);
            System.out.print("Verification code:");

            // open up standard input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String verificationCode = br.readLine();

            if (isVerificationCode(verificationCode)) {
                // Now that we have that, we move on to get the refresh & access tokens
                return new ClientSecretAuthentication(chatterData, verificationCode).authenticate();
            } else {
                System.err.println("Didn't receive a valid Verification code, please try again...");

                attempts--;
                if (attempts == 0) {
                    keepTrying = false;
                    System.err.println("3 failed attempts, stopping...");
                }
            }
        }

        throw new UnauthenticatedSessionException("Missing clientcode...");
    }
}
