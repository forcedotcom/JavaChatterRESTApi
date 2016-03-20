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

import com.salesforce.chatter.attachment.ImageAttachment;
import org.junit.Test;

import com.salesforce.chatter.attachment.LinkAttachment;
import com.salesforce.chatter.message.LinkSegment;
import com.salesforce.chatter.message.Message;
import com.salesforce.chatter.message.TextSegment;

import java.io.File;

public class ChatterCommandsTest {

    ChatterCommands commandTool = new ChatterCommands();

    @Test
    public void testGetJsonPayloadWithMultipleSegments() throws Exception {
        Message message = new Message();
        message.addSegment(new TextSegment("Check out this site! "));
        message.addSegment(new LinkSegment("http://www.salesforce.com"));

        assertEquals("{\"body\":{\"messageSegments\":[{\"text\":\"Check out this site! \",\"type\":\"text\"},{\"type\":\"link\",\"url\":\"http://www.salesforce.com\"}]}}",
            commandTool.getJsonPayload(message));
    }

    @Test
    public void testGetJsonPayloadWithMultipleSegmentsAndAttachment() throws Exception {
        Message message = new Message();
        message.addSegment(new TextSegment("Check out this site! "));
        message.addSegment(new LinkSegment("http://www.salesforce.com"));
        message.addAttachment(new LinkAttachment("http://www.salesforce.com", "Salesforce"));

        assertEquals(
            "{\"body\":{\"messageSegments\":[{\"text\":\"Check out this site! \",\"type\":\"text\"},{\"type\":\"link\",\"url\":\"http://www.salesforce.com\"}]},\"attachment\":{\"attachmentType\":\"Link\",\"url\":\"http://www.salesforce.com\",\"urlName\":\"Salesforce\"}}",
            commandTool.getJsonPayload(message));
    }

    @Test
    public void testGetJsonPayloadWithMultipleSegmentsAndImageAttachment() throws Exception {
        Message message = new Message();
        message.addSegment(new TextSegment("Check out this site! "));
        File img = new File("img.png");
        message.addAttachment(new ImageAttachment("Title", "Description", "image/png", img));

        assertEquals(
            "{\"body\":{\"messageSegments\":[{\"text\":\"Check out this site! \",\"type\":\"text\"}]},\"attachment\":{\"attachmentType\":\"NewFile\",\"title\":\"Title\",\"description\":\"Description\",\"fileName\":\"img.png\"}}",
            commandTool.getJsonPayload(message));
    }

}
