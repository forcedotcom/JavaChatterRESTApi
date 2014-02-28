package com.salesforce.chatter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.salesforce.chatter.attachment.LinkAttachment;
import com.salesforce.chatter.message.LinkSegment;
import com.salesforce.chatter.message.Message;
import com.salesforce.chatter.message.TextSegment;

public class ChatterCommandsTest {

	ChatterCommands commandTool = new ChatterCommands();

	@Test
	public void testGetJsonPayloadWithMultipleSegments() throws Exception {
		Message message = new Message();
		message.addSegment(new TextSegment("Check out this site! "));
		message.addSegment(new LinkSegment("http://www.salesforce.com"));
		
		assertEquals("{\"body\":{\"messageSegments\":[{\"text\":\"Check out this site! \",\"type\":\"text\"},{\"type\":\"link\",\"url\":\"http://www.salesforce.com\"}]}}", commandTool.getJsonPayload(message));
	}

	@Test
	public void testGetJsonPayloadWithMultipleSegmentsAndAttachment() throws Exception {
		Message message = new Message();
		message.addSegment(new TextSegment("Check out this site! "));
		message.addSegment(new LinkSegment("http://www.salesforce.com"));
		message.addAttachment(new LinkAttachment("http://www.salesforce.com", "Salesforce"));
		
		assertEquals("{\"body\":{\"messageSegments\":[{\"text\":\"Check out this site! \",\"type\":\"text\"},{\"type\":\"link\",\"url\":\"http://www.salesforce.com\"}]},\"attachment\":{\"attachmentType\":\"Link\",\"url\":\"http://www.salesforce.com\",\"urlName\":\"Salesforce\"}}", commandTool.getJsonPayload(message));
	}

}
