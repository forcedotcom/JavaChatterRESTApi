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

import org.apache.commons.httpclient.HttpMethod;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.ChatterAuthToken;
import com.salesforce.chatter.authentication.ChatterAuthenticate;
import com.salesforce.chatter.authentication.IChatterData;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;
import com.salesforce.chatter.authentication.methods.AuthentificationMethod;
import com.salesforce.chatter.commands.ChatterCommand;
import com.salesforce.chatter.commands.PostToStatusCommand;
import com.salesforce.chatter.message.Message;
import com.salesforce.chatter.message.TextSegment;

public class ChatterServiceTest {

    /**
     * <p>The most simple test. Construct a simple message and post it to the users wall.</p>
     * 
     * @throws IOException
     * @throws JSONException
     * @throws UnauthenticatedSessionException
     * @throws AuthenticationException
     */
    @Test
    public void testUpdateStatus() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("jUnit Chatter message!"));

        ChatterCommand cmd = new PostToStatusCommand();

        IChatterData data = mock(IChatterData.class);

        ChatterAuthToken token = new ChatterAuthToken("abc");

        // Mock setChatterAuthenticate
        ChatterAuthenticate mockAuth = mock(ChatterAuthenticate.class);
        AuthentificationMethod mockMethod = mock (AuthentificationMethod.class);
        when(mockMethod.authenticate()).thenReturn(token);
        when(mockAuth.getAuthentificationMethod(data)).thenReturn(mockMethod);

        //Tools
        ChatterTools tools = new ChatterTools(data);
        tools = spy(tools);
        Mockito.doNothing().when(tools).executeMethod(any(HttpMethod.class));
        
        ChatterService service = new ChatterService(data);
        service.setChatterAuthenticate(mockAuth);
        service.setChatterTools(tools);

        service.executeCommand(cmd, msg);
    }

    /**
     * <p>The most simple test. Construct a simple message and post it to the users wall.</p>
     * 
     * @throws IOException
     * @throws JSONException
     * @throws UnauthenticatedSessionException
     * @throws AuthenticationException
     */
    @Test
    public void testAuthenticated() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("jUnit Chatter message!"));

        ChatterCommand cmd = new PostToStatusCommand();

        IChatterData data = mock(IChatterData.class);

        ChatterAuthToken token = new ChatterAuthToken("abc");

        // Mock setChatterAuthenticate
        ChatterAuthenticate mockAuth = mock(ChatterAuthenticate.class);
        AuthentificationMethod mockMethod = mock (AuthentificationMethod.class);
        when(mockMethod.authenticate()).thenReturn(token);
        when(mockAuth.getAuthentificationMethod(data)).thenReturn(mockMethod);

        //Tools
        ChatterTools tools = new ChatterTools(data);
        tools = spy(tools);
        Mockito.doNothing().when(tools).executeMethod(any(HttpMethod.class));
        
        ChatterService service = new ChatterService(data);
        service.setChatterAuthenticate(mockAuth);
        service.setChatterTools(tools);

        service.executeCommand(cmd, msg);
        service.executeCommand(cmd, msg);
    }
}


