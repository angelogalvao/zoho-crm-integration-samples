package com.angelogalvao.samples;

import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.List;

import com.zoho.crm.library.api.response.BulkAPIResponse;
import com.zoho.crm.library.exception.ZCRMException;
import com.zoho.crm.library.setup.restclient.ZCRMRestClient;
import com.zoho.crm.library.setup.users.ZCRMUser;
import com.zoho.oauth.client.ZohoOAuthClient;
import com.zoho.oauth.common.ZohoOAuthException;

/**
 * @author Ângelo Galvão
 * 
 * SCOPES for this test: AAAserver.profile.Read,ZohoCRM.users.all
 */
public class Application {

    ZCRMRestClient zohoRestClient;

    public static void main(String[] args) {

        // Getting the system properties to connect to Zoho CRM API
        // This demo expect that you already authenticate in Zoho CRM using Self Client. 

        String clientId     = System.getProperty("clientId");
        String clientSecret = System.getProperty("clientSecret");
        String grantToken   = System.getProperty("grantToken");
        String currentUser  = System.getProperty("currentUser");

        if (clientId == null || clientSecret == null || grantToken == null || currentUser == null) {
            System.err.println(
                    "You pass the clientId, clientSecret, grantToken and currentUser variables to the application!");
            System.err.println(
                    "Example: java -DclientId=abc -DclientSecret=def -DgrantToken=ghi -DcurrentUser=jkl com.angelogalvao.samples.Application");

            System.exit(-1);
        }

        // Executing the integration with Zoho CRM 
        Application app = new Application(clientId, clientSecret, grantToken, currentUser);

        app.runGetAllUsersTest();

    }

    private Application(String clientId, String clientSecret, String grantToken, String currentUser) {

        // Configure the REST client configuration map
        HashMap<String, String> config = createonfigurationMap(clientId, clientSecret, currentUser);

        try {
            // Initialize the REST client with the configuration map - This is the only way to configure for Java SDK version 2.x
            ZCRMRestClient.initialize(config);
        } catch (Exception e) {
            System.err.println("Error initializing Zoho CRM Rest Client");
            System.exit(-1);
        }

        // Client to get the access token  
        ZohoOAuthClient oauthClient = ZohoOAuthClient.getInstance();

        try {

            // Get the access token from the grant token  - the grant token is generated using the Self Client. 
            oauthClient.generateAccessToken(grantToken);
        } catch (ZohoOAuthException e) {
            System.err.println("Error generating access token");
            System.exit(-1);
        }

        // Get the instance of the rest client - with all tokens in place. 
        zohoRestClient = ZCRMRestClient.getInstance();
    }

    private HashMap<String, String> createonfigurationMap(String clientId, String clientSecret, String currentUser) {

        HashMap<String, String> config = new HashMap<>();

        config.put("client_id", clientId);
        config.put("client_secret", clientSecret);
        config.put("currentUserEmail", currentUser);
        config.put("minLogLevel", "WARN");
        config.put("persistence_handler_class", "com.zoho.oauth.clientapp.ZohoOAuthFilePersistence");
        config.put("oauth_tokens_file_path",
                FileSystems.getDefault().getPath("").toAbsolutePath() + "/oauthtokens.properties");

        return config;
    }

    public void runGetAllUsersTest() {

        BulkAPIResponse response = null;
        try {
            // Calling the Zoho API resource 
            response = zohoRestClient.getOrganizationInstance().getAllUsers();            
        } catch (ZCRMException e) {
            System.err.println("Error get the users");
            System.exit(-1);
        }
        
        // Interating the response data. 
        List<ZCRMUser> users = (List<ZCRMUser>) response.getData();
        
        for(ZCRMUser userInstance : users ){

            System.out.println(">>> User: " + userInstance.getFirstName() + ", E-mail: " + userInstance.getEmailId() );
        }
    }
}