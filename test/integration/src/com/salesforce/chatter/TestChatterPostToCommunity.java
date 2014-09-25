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

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;
import com.salesforce.chatter.commands.ChatterCommand;
import com.salesforce.chatter.commands.PostToGroupCommand;
import com.salesforce.chatter.message.LinkSegment;
import com.salesforce.chatter.message.Message;
import com.salesforce.chatter.message.TextSegment;

/**
 * <p>These are integration tests to validate posting to a group in a community.</p>
 * 
 * <p>These tests depend on 3 things to succeed:<ul>
 * <li>A valid GROUP_ID</li>
 * <li>A valid COMMUNITY_ID</li>
 * <li>The {@link ChatterData} object to contain valid data</li>
 * </ul>
 * </p>
 * 
 * @author Eric Broyles
 * @since 1.0.3
 */
public class TestChatterPostToCommunity {

    private static final String GROUP_ID = "0F9e00000008UnA";
    private static final String COMMUNITY_ID = "0ACeCOMMUNITY13FCC";

    @Test
    public void testPostOnGroup() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("sendChatter "));
        msg.addSegment(new TextSegment(" done!"));
        msg.addSegment(new LinkSegment("www.salesforce.com"));

        ChatterCommand cmd = new PostToGroupCommand(GROUP_ID, COMMUNITY_ID);
        ChatterService service = new ChatterService(new ChatterData());

        ChatterResponse response = service.executeCommand(cmd, msg);

        assertEquals("Response status", 201, response.getStatusCode());
    }
}
