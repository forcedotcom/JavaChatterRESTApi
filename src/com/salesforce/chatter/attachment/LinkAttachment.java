package com.salesforce.chatter.attachment;

/**
 * Represents an "Attachment Input: Link" item in the Chatter REST API.
 * 
 * @author Eric Broyles
 * @version $Id:$
 * 
 */
public class LinkAttachment extends Attachment {

	private static final String LINK_TYPE = "Link";
	private String attachmentType = LINK_TYPE;
	private String url;
	private String urlName;

	/**
	 * Construct a LinkAttachment object for a url with a name.
	 * 
	 * @param url
	 * @param urlName
	 */
	public LinkAttachment(String url, String urlName) {
		this.url = url;
		this.urlName = urlName;
	}

	/**
	 * Construct a LinkAttachment object for a url. A name is generated from the
	 * domain name of the URL
	 * 
	 * @param url
	 */
	public LinkAttachment(String url) {
		this.url = url;
	}

	@Override
	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

}
