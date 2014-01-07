package com.salesforce.chatter;

import java.io.IOException;

import org.apache.commons.httpclient.HttpMethod;

/**
 * <p>
 * Wrapper for HttpMethod to encapsulate response data from an executed method.
 * This primarily provides access to the response code and the response body.
 * </p>
 * 
 * @author Eric Broyles
 * @version $Id:$
 * 
 */
public class ChatterResponse {

	private HttpMethod method;

	public ChatterResponse(HttpMethod method) {
		this.method = method;
	}

	public int getStatusCode() {
		return method.getStatusCode();
	}

	public String getStatusText() {
		return method.getStatusText();
	}

	public String getStatusLine() {
		try {
			return method.getStatusLine().toString();
		} catch (NullPointerException e) {
			return null;
		}
	}

	public String getResponseBodyAsString() throws IOException {
		return method.getResponseBodyAsString();
	}

}
