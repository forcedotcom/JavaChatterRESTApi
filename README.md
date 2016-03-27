# Java Chatter REST API

Think the Chatter REST API is awesome and powerful, but maybe a bit overwhelming? This native Java implementation aims to keep the implementation simple
while keeping all the power at your fingertips.
 
## Build status
![Build status][build-status]
- Atlassian Project: https://jasperroel.atlassian.net/builds/browse/JCRA-JCRA
- Code coverage report: https://jasperroel.atlassian.net/builds/browse/JCRA-JCRA/latest/artifact/JOB1/Clover-Report-%28System%29/dashboard.html

## Overview
This Java Chatter REST API is a simple library using POJO's to interact with the Chatter REST API.

## Usage
Simply download the java/src and required libraries and incorporate it into your project.

Compiling the source is easy using gradle:

    gradle assemble

Or to run the unittests as well

    gradle build

The jar file can be found at build/lib/JavaChatterRESTApi.jar.

## Features
- Creating Chatter messages, including text, links, @mentions and #tags
- Posting your status (on your own wall)
- Posting to somebody else's wall
- Posting on a group
- Replying to a thread

- Searching for users
- Executing "raw" SOQL queries in case you need some advanced usage*

* The SOQL queries are executed through the REST API

## Configuration and authentication
The Java Chatter REST API supports various forms of authentication.
All of them require a form of the IChatterData information to be fed into them, but different methods require different parts of this object to be filled in. 

### ClientSecret authentication

Also see http://www.salesforce.com/us/developer/docs/api_streaming/Content/code_sample_auth_oauth.htm for how this works.

### RefreshToken authentication
If you already possess a Refresh token (from a previous client-secret authentication perhaps) you can use this authentication method.

### Username and password
If your org allows it (grant_type=password), you can use the client ID, client Secret and your username and password to authenticate.
This is discouraged and the ClientSecret authentication is preferred over this one.

### Interactive authentication
This requests information from the user to be returned via the console at runtime. Useful for token-less environments.
It does requires a minimum of a Client Key and Client Callback to be configured.

### Example of a configuration file/class to be used with Password authentication
```java
public class ChatterData implements IChatterData {

    private final String apiVersion = "24.0";
    private final String instanceUrl = "https://na10.salesforce.com";

    private final ChatterAuthMethod authMethod = ChatterAuthMethod.PASSWORD;
    private final String username = "myApiUser@myorganisation.com";
    private final String password = "myPassword";

    private final String clientKey = "3MVG9yZ.WNe6byQDS1oBDJg6vP82qy7w.OVregoIATuJtBxxIxDQmb8kr8zmasqSUAsCED6CCNx.3zaWScqph";
    private final String clientSecret = "6830641966138152974";
    
    // And the required get/set methods
}
```

## Examples
You can find loads of practical examples in the test/integration/src/ folder under the com.salesforce.chatter package.
For example: [TestChatterPost.java](test/integration/src/com/salesforce/chatter/TestChatterPost.java)

A very simple example:
```java
ChatterService service = new ChatterService(new ChatterData());

Message msg = new Message();
msg.addSegment(new TextSegment("Hey "));
msg.addSegment(new MentionSegment(JASPER_PROFILE_ID));
msg.addSegment(new TextSegment(", check out this website: "));
msg.addSegment(new LinkSegment("www.salesforce.com"));
msg.addSegment(new TextSegment(" #salesforce"));

File img = new File("img.png");
msg.addAttachment(new ImageAttachment("Title", "Description", "image/png", img));

ChatterCommand cmd = new PostToThreadCommand(TEAM_GROUP_ID);

service.executeCommand(cmd, msg);
```

This contains almost all information you need.
You create a simple Message object which holds whatever it is you would like to post (and which supports text, links and @mentions).
The ChatterCommand decides where this message should go (to your own or somebody's wall, a particular group or as a response to somebody's thread).
The ChatterData holds all the authentication information while the ChatterService takes care of making the magic happen

## License
The BSD 2-Clause License

http://opensource.org/licenses/BSD-2-Clause

See [LICENSE.txt](./LICENSE.txt)

[build-status]: https://jasperroel-bamboo-agent.herokuapp.com/status/JCRA-JCRA "Build status"
