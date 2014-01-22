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

import org.junit.Test;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;
import com.salesforce.chatter.commands.ChatterCommand;
import com.salesforce.chatter.commands.FindUserCommand;
import com.salesforce.chatter.message.Message;

/**
 * <p>A quick example of how to look up a user in your Salesforce.com environment via the Chatter API.</p>
 * 
 * <p> The {@link ChatterData} object has to contain valid data for this test to succeed.</li>
 * 
 * @author jroel
 * @since 1.0
 * 
 */
public class ChatterFindTest {

    @Test
    public void testUpdateStatus() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        ChatterCommand cmd = new FindUserCommand("Jasper+Roel");
        Message msg = new Message();

        ChatterService service = new ChatterService(new ChatterData());
        service.executeCommand(cmd, msg);
    }
}
