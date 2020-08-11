package com.angelogalvao.samples;

import java.nio.file.FileSystems;
import java.util.HashMap;

import com.zoho.crm.library.setup.restclient.ZCRMRestClient;
import com.zoho.oauth.client.ZohoOAuthClient;
import com.zoho.oauth.contract.ZohoOAuthTokens;

/**
 * Class that generates the access token. 
 * 
 * Generate a sef client with the scopes: AAAserver.profile.Read,ZohoCRM.users.all
 * 
 * @author Ângelo Galvão
 */
public class GenerateAccessToken {

    public static void main(String[] args) throws Exception {

        String clientId     = System.getProperty("clientId");
        String clientSecret = System.getProperty("clientSecret");
        String grantToken   = System.getProperty("grantToken");

        if (clientId == null || clientSecret == null || grantToken == null ) {
            System.err.println(
                    "You pass the clientId, clientSecret and grantToken  variables to the application!");
            System.err.println(
                    "Example: java -DclientId=abc -DclientSecret=def -DgrantToken=ghi com.angelogalvao.samples.GenerateAccessToken");

            System.exit(-1);
        }


        ZCRMRestClient.initialize(getConfigurationMap(clientId,clientSecret));

        ZohoOAuthClient cli = ZohoOAuthClient.getInstance();

        ZohoOAuthTokens tokens = cli.generateAccessToken(grantToken);


        System.out.println(">>>> grantToken" + grantToken + " >>>> accessToken : " + tokens.getAccessToken() + " >>>>> ref token :" + tokens.getRefreshToken());
    }

    private static HashMap<String, String> getConfigurationMap(String clientId, String clientSecret ) {
        HashMap<String, String> config = new HashMap<>();

        config.put("client_id",clientId);
        config.put("client_secret",clientSecret);

        config.put("minLogLevel", "WARN");
        config.put("persistence_handler_class","com.zoho.oauth.clientapp.ZohoOAuthFilePersistence");
        config.put("oauth_tokens_file_path", FileSystems.getDefault().getPath("").toAbsolutePath() + "/oauthtokens.properties");

        return config;
    }
}