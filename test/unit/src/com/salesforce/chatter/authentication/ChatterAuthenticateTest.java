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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.salesforce.chatter.authentication.methods.AuthentificationMethod;
import com.salesforce.chatter.authentication.methods.ClientSecretAuthenication;
import com.salesforce.chatter.authentication.methods.InteractiveAuthentication;
import com.salesforce.chatter.authentication.methods.RefreshTokenAuthentication;
import com.salesforce.chatter.authentication.methods.UsernamePasswordAuthentication;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ChatterAuthMethod.class)
public class ChatterAuthenticateTest {

    @Test
    public void testFactory() throws AuthenticationException {
        ChatterAuthenticate auth = new ChatterAuthenticate();
        AuthentificationMethod method;

        IChatterData chatterData = mock(IChatterData.class);

        when(chatterData.getAuthMethod()).thenReturn(ChatterAuthMethod.PASSWORD);
        method = auth.getAuthentificationMethod(chatterData);
        assertEquals(UsernamePasswordAuthentication.class, method.getClass());

        when(chatterData.getAuthMethod()).thenReturn(ChatterAuthMethod.INTERACTIVE);
        method = auth.getAuthentificationMethod(chatterData);
        assertEquals(InteractiveAuthentication.class, method.getClass());

        when(chatterData.getAuthMethod()).thenReturn(ChatterAuthMethod.CLIENT_SECRET);
        method = auth.getAuthentificationMethod(chatterData);
        assertEquals(ClientSecretAuthenication.class, method.getClass());

        when(chatterData.getAuthMethod()).thenReturn(ChatterAuthMethod.REFRESH_TOKEN);
        method = auth.getAuthentificationMethod(chatterData);
        assertEquals(RefreshTokenAuthentication.class, method.getClass());
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidAuthMethod() throws AuthenticationException {
        ChatterAuthenticate auth = new ChatterAuthenticate();

        IChatterData chatterData = mock(IChatterData.class);
        when(chatterData.getAuthMethod()).thenReturn(null);
        auth.getAuthentificationMethod(chatterData);
        fail("We should have gotten a NullPointerException by now.");
    }

    @Test(expected = AuthenticationException.class)
    public void test() throws AuthenticationException {
        ChatterAuthMethod C = PowerMockito.mock(ChatterAuthMethod.class);
        Whitebox.setInternalState(C, "name", "BAD");
        Whitebox.setInternalState(C, "ordinal", 4);

        PowerMockito.mockStatic(ChatterAuthMethod.class);
        PowerMockito.when(ChatterAuthMethod.values()).thenReturn(new ChatterAuthMethod[] {
            ChatterAuthMethod.PASSWORD, ChatterAuthMethod.INTERACTIVE,
            ChatterAuthMethod.CLIENT_SECRET, ChatterAuthMethod.REFRESH_TOKEN, C });

        ChatterAuthenticate auth = new ChatterAuthenticate();

        IChatterData chatterData = mock(IChatterData.class);
        when(chatterData.getAuthMethod()).thenReturn(C);

        auth.getAuthentificationMethod(chatterData);
        fail("We should have gotten a AuthenticationException by now.");
    }
}
