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
import java.net.URLEncoder;

import org.junit.Test;

import com.salesforce.chatter.authentication.AuthenticationException;
import com.salesforce.chatter.authentication.UnauthenticatedSessionException;
import com.salesforce.chatter.commands.ChatterCommand;
import com.salesforce.chatter.commands.FindUserCommand;
import com.salesforce.chatter.commands.PostToGroupCommand;
import com.salesforce.chatter.commands.PostToProfileCommand;
import com.salesforce.chatter.commands.PostToStatusCommand;
import com.salesforce.chatter.commands.PostToThreadCommand;
import com.salesforce.chatter.commands.SOQLCommand;
import com.salesforce.chatter.message.LinkSegment;
import com.salesforce.chatter.message.MentionSegment;
import com.salesforce.chatter.message.Message;
import com.salesforce.chatter.message.TextSegment;

/**
 * <p>These are a series of integration tests build to test all the basic Message formats,
 * Commands and authentication methods.</p>
 * 
 * <p>These tests depend on 3 things to succeed:<ul>
 * <li>A valid USER_ID</li>
 * <li>A valid GROUP_ID</li>
 * <li>The {@link ChatterData} object to contain valid data</li>
 * </ul>
 * </p>
 * 
 * @author jroel
 * @since 1.0
 */
public class TestChatterPost {

    private final String SOME_USER_ID = "005A0000USER0ID";
    private final String SOME_GROUP_ID = "0F9F000GROUP0ID";

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

        ChatterService service = new ChatterService(new ChatterData());
        service.executeCommand(cmd, msg);
    }

    @Test
    public void testPostOnGroup() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("sendChatter "));
        msg.addSegment(new MentionSegment(SOME_USER_ID));
        msg.addSegment(new TextSegment(" done!"));
        msg.addSegment(new LinkSegment("www.salesforce.com"));

        ChatterCommand cmd = new PostToGroupCommand(SOME_GROUP_ID);

        ChatterService service = new ChatterService(new ChatterData());
        service.executeCommand(cmd, msg);
    }

    @Test
    public void testPostOnProfile() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("sendChatter "));
        msg.addSegment(new MentionSegment(SOME_USER_ID));
        msg.addSegment(new TextSegment(" done!"));
        msg.addSegment(new LinkSegment("www.salesforce.com"));

        ChatterCommand cmd = new PostToProfileCommand(SOME_USER_ID);

        ChatterService service = new ChatterService(new ChatterData());
        service.executeCommand(cmd, msg);
    }

    @Test
    public void testPostOnThread() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("sendChatter "));
        msg.addSegment(new MentionSegment(SOME_USER_ID));
        msg.addSegment(new TextSegment(" done!"));
        msg.addSegment(new LinkSegment("www.salesforce.com"));
        msg.addSegment(new TextSegment(" #opencmsnotification"));

        ChatterCommand cmd = new PostToThreadCommand("0D5F000000jk29J");

        ChatterService service = new ChatterService(new ChatterData());
        service.executeCommand(cmd, msg);
    }

    @Test
    public void testPostInteractiveLogin() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        Message msg = new Message();
        msg.addSegment(new TextSegment("Testing interactive login"));

        ChatterCommand cmd = new PostToStatusCommand();

        ChatterService service = new ChatterService(new ChatterAuthInteractive());
        service.executeCommand(cmd, msg);
    }

    @Test
    public void testChatterSearch() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        String search = "jroel@salesforce.com"; // no results: "users" : [ ]
        search = "Jasper Roel"; // Invalid uri
        search = URLEncoder.encode(search, "UTF-8");
        // search = "Jasper%20Roel"; // Valid uri, with 1 result
        ChatterCommand cmd = new FindUserCommand(search);

        ChatterService service = new ChatterService(new ChatterData());
        ChatterResponse result = service.executeCommand(cmd);

        // headers
        System.out.println(result.getResponseBodyAsString());

    }

    @Test
    public void testChatterSOQL() throws IOException, UnauthenticatedSessionException, AuthenticationException {
        String command = "Select Id, Name From User WHERE Email = 'jroel@salesforce.com'"; // 3 results from my dev Org
        command = "Select Id, Username From User WHERE Email = 'jroel@salesforce.com' AND Name LIKE '%Jasper Roel%'";
        command = URLEncoder.encode(command, "UTF-8");
        ChatterCommand cmd = new SOQLCommand(command);

        ChatterService service = new ChatterService(new ChatterData());
        ChatterResponse result = service.executeCommand(cmd);

        // headers
        System.out.println(result.getResponseBodyAsString());

    }
}
