package com.angelogalvao.samples;

import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.List;

import com.zoho.crm.library.api.response.BulkAPIResponse;
import com.zoho.crm.library.setup.restclient.ZCRMRestClient;
import com.zoho.crm.library.setup.users.ZCRMUser;

/**
 * Get the grant token
 * 
 * All permissions: ,ZohoCRM.modules.all,ZohoCRM.settings.all,ZohoCRM.bulk.all,ZohoCRM.notifications.read,ZohoCRM.notifications.create,ZohoCRM.notifications.update,ZohoCRM.notifications.delete,ZohoCRM.coql.read,ZohoCRM.users.all,ZohoCRM.org.all
 */
public final class GetAllUsersExample {

    /**
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception {

        String currentUser  = System.getProperty("currentUser");

        if ( currentUser == null ) {
            System.err.println("You pass the currentUser variables to the application!");
            System.err.println("Example: java -DcurrentUser=jkl com.angelogalvao.samples.GetAllUsersExample");

            System.exit(-1);
        }


        ZCRMRestClient.initialize(getConfigurationMap(currentUser));
        ZCRMRestClient client = ZCRMRestClient.getInstance();

		BulkAPIResponse response = client.getOrganizationInstance().getAllUsers();
        List<ZCRMUser> users = (List<ZCRMUser>) response.getData();
        
        for(ZCRMUser userInstance : users ){

            System.out.println(">>> User: " + userInstance.getFirstName() + ", E-mail: " + userInstance.getEmailId() );
        }
        
    }

    private static HashMap<String, String> getConfigurationMap(String currentUser) {
        HashMap<String, String> config = new HashMap<>();

        config.put("minLogLevel", "WARN");
        config.put("currentUserEmail",currentUser);
        config.put("persistence_handler_class","com.zoho.oauth.clientapp.ZohoOAuthFilePersistence");
        config.put("oauth_tokens_file_path", FileSystems.getDefault().getPath("").toAbsolutePath() + "/oauthtokens.properties");

        return config;
    }
}
