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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.salesforce.chatter.message.Message;
import com.salesforce.chatter.message.MessageSegment;

public class ChatterCommands {

    /**
     * <p>This creates a HttpMethod with the message as its payload. The message should be a properly formatted JSON
     * String (No validation is done on this).</p>
     * 
     * <p>The message can be easily created using the {@link #getJsonPayload(Message)} method.</p>
     * 
     * @param uri The full URI which we will post to
     * @param message A properly formatted JSON message. UTF-8 is expected
     * @throws IOException
     */
    public HttpMethod getJsonPost(String uri, String message) throws IOException {
        PostMethod post = new PostMethod(uri);
        post.setRequestHeader("Content-type", "application/json");
        post.setRequestEntity(new StringRequestEntity(message, "application/json", "UTF-8"));
        return post;
    }

    /**
     * <p>Creates a JSON String from the message segments, ready to be consumed by the Salesforce.com API.</p>
     * 
     * <p>The result is ready to be consumed by {@link #getJsonPost(String, String)}.</p>
     * 
     * @param message The message which should be send to the Salesforce.com API
     * @return A (JSON) String with all the segments embedded
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public String getJsonPayload(Message message) throws JsonGenerationException, JsonMappingException, IOException {
        List<Map<String, String>> segments = new LinkedList<Map<String, String>>();
        for (MessageSegment segment : message.getSegments()) {
            Map<String, String> s = new HashMap<String, String>();
            s.put(MessageSegment.TYPE_KEY, segment.getTypeValue());
            s.put(segment.getSegmentKey(), segment.getSegmentValue());
            segments.add(s);
        }

        Map<String, List<Map<String, String>>> messageSegments = new HashMap<String, List<Map<String, String>>>();
        Map<String, Map<String, List<Map<String, String>>>> json = new HashMap<String, Map<String, List<Map<String, String>>>>();

        messageSegments.put("messageSegments", segments);
        json.put("body", messageSegments);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(json);

    }
}
