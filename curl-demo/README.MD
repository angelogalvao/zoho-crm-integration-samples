# How to integrate with ZohoCRM using Curl

- If you're using windows, download curl at: https://curl.haxx.se/download.html

## Generate the Grant token - Authenticate the client app using Self Client application

To create the grant token you should create a Self Client and generate the token from this client. The steps to generate the token is detailed bellow:

1. Go to developer console: https://api-console.zoho.com/
2. Create a Self Client Application. 
3. On the Generate Code tab create the Grant Token with the following parameters:

- Scope: This is the access permissions for the Zoho domain objects. 
  - For this example, add the user scope: ZohoCRM.users.all
- Time Duration: How long the grant token will be valid.
- Scope Description: Any description.
  - For this example: Users.
- Then click in Create Button and choose the Portal that you'll access and click create again.

4. Copy the generated token. 

## Create the access token

To create the access token, make a POST request to https://accounts.zoho.com/oauth/v2/token API resource with the following parameters:

Parameter | Value
--------- | -----
grant_type| authorization_code
client_id | The generated client_id value from the Self Client Application
client_secret | The generated client_secret value from the Self Client Application
code | The *grant token* generated in the previous step.

This is a example request using curl (just replace the values with the actual value):

```
curl -d "grant_type=authorization_code&client_id=100.xxx&client_secret=xxx&code=1000.abc" -X POST https://accounts.zoho.com/oauth/v2/token
```
The response of this servic should be a json object like this:

```
{"access_token":"1000....",
"refresh_token":"1000.....",
"api_domain":"https://www.zohoapis.com",
"token_type":"Bearer",
"expires_in":3600}
```


# Call the Zoho API

For this example, get all the users from the ZohoCRM instance. Take notice that you should add the access token that you generated in the previous step in front of Authorization: Zoho-oauthtoken HTTP header. 

```
curl "https://www.zohoapis.com/crm/v2/users?type=AllUsers" \
    -X GET \
    -H "Authorization: Zoho-oauthtoken 1000.xyz"
```



