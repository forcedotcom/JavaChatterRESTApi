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
package com.salesforce.chatter.message;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testEmptyMessage() {
        Message message = new Message();
        assertNotNull("Default message should has null segments", message.getSegments());
        assertEquals("Default message should be empy", 0, message.getSegments().size());
    }

    @Test
    public void testOneItemInMessage() {
        Message message = new Message();
        MessageSegment text = new TextSegment(null);

        message.addSegment(text);

        assertEquals("message should only contain this one TextSegment", 1, message.getSegments().size());
        assertEquals("message should contain same TextSegment", text, message.getSegments().get(0));
    }

    @Test
    public void testOrderInMessage() {
        Message message = new Message();
        MessageSegment text0 = new TextSegment(null);
        MessageSegment text1 = new LinkSegment(null);
        MessageSegment text2 = new MentionSegment(null);
        MessageSegment text3 = new TextSegment(null);

        message.addSegment(text0);
        message.addSegment(text1);
        message.addSegment(text2);
        message.addSegment(text3);

        assertEquals("message should only contain all 4 TextSegments", 4, message.getSegments().size());
        List<MessageSegment> segments = message.getSegments();
        assertEquals(text0, segments.get(0));
        assertEquals(text1, segments.get(1));
        assertEquals(text2, segments.get(2));
        assertEquals(text3, segments.get(3));
    }

}
