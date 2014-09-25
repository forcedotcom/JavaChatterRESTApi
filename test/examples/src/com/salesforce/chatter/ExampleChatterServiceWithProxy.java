package com.salesforce.chatter;

import org.apache.commons.httpclient.HttpClient;

import com.salesforce.chatter.authentication.IChatterData;
import com.salesforce.chatter.commands.ChatterCommand;

/**
 * <p>This is not a real test, but a common example of working with a proxy.</p>
 * 
 * <p>In this case, we create a HttpClient ourselves (which currently is the default for a ChatterService anyway) and
 * give that (with the proxy preconfigured) to the ChatterService.</p>
 * 
 * @author jroel
 * 
 */
public class ExampleChatterServiceWithProxy {

    /**
     * <p>This sets a proxy host and port in a default {@link HttpClient}, which we then pass into the
     * ChatterService.</p>
     * 
     * <p>We can then reuse the chatterService, knowing it will use the proxy for every {@link ChatterCommand}.
     */
    public void testChatterServiceWithProxy() {
        String proxyHost = "proxy-host.example.net";
        int proxyPort = 8080;

        HttpClient httpClient = new HttpClient();
        httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);

        IChatterData chatterData = null;
        ChatterService chatterService = new ChatterService(chatterData);

        chatterService.setHttpClient(httpClient);
    }
}
