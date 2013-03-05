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
package com.salesforce.chatter.commands;

import java.security.InvalidParameterException;

/**
 * <p>The result is a JSON array with users.</p>
 * 
 * <p>It can be used for auto-complete or other purposes. Uses a "fuzzy search".</p>
 * 
 * <p>If you require more precise results, look into @{link SOQLCommand}.
 * 
 * @see <a
 *      href="http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_resources_how_to.htm#cc_autocomplete">http://www.salesforce.com/us/developer/docs/chatterapi/Content/connect_resources_how_to.htm#cc_autocomplete</a>
 * @author jroel
 * @since 1.0
 * 
 */
public class FindUserCommand implements ChatterCommand {

    public static final String URI = "/chatter/users?q=";

    /**
     * <p>Any URL encoded String. Allows wildcards (like "Jasp*").</p>
     */
    private final String username;

    /**
     * <p>Username needs to be URL encoded, you're free to use wildcards ("Jasp*").</p>
     * 
     * @param username
     */
    public FindUserCommand(String username) {
        if (username == null) {
            throw new InvalidParameterException(
                "Unable to find a user without specifying the username in question. Please pass a valid username.");
        }
        this.username = username;
    }

    /**
     * <p>Returns the username.</p>
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

    @Override
    public String getURI() {
        return URI + username;
    }
}
