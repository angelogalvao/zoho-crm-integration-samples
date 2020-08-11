# How to integrate with ZohoCRM using Java SDK

- Install Apache Maven in your enviroment. 

## Generate the Grant token - Authenticate the client app using Self Client application

To create the grant token you should create a Self Client and generate the token from this client. The steps to generate the token is detailed bellow:

1. Go to developer console: https://api-console.zoho.com/
2. Create a Self Client Application. 
3. On the Generate Code tab create the Grant Token with the following parameters:

- Scope: This is the access permissions for the Zoho domain objects. The scope AAAserver.profile.Read is required. 
  - For this example, add the user scope: AAAserver.profile.Read,ZohoCRM.users.all
- Time Duration: How long the grant token will be valid.
- Scope Description: Any description.
  - For this example: Users.
- Then click in Create Button and choose the Portal that you'll access and click create again.

4. Copy the generated token. 

## Execute the Java Application

To execute the Java Application you should pass the following system properties to the Java application:

Parameter | Value
--------- | -----
clientId | The generated *client_id* value from the Self Client Application
clientSecret | The generated *client_secret* value from the Self Client Application
grantToken | The generated *grant token*  in the previous step.
currentUser | The email ID of the current user (the one that you generated the Self Client Application).

This is a example command to execute the Java application:

```
mvn -DclientId=1000.... -DclientSecret=1000.... -DgrantToken=1000.... -DcurrentUser=test@test.com exec:java -Dexec.mainClass="com.angelogalvao.samples.Application"
```

## Execute the Java Application in 2 steps

If you want, you can  create the access tokens: 

```
mvn -DclientId=1000.... -DclientSecret=1000.... -DgrantToken=1000.... exec:java -Dexec.mainClass="com.angelogalvao.samples.GenerateAccessToken"
```

And then, you can call the Zoho CRM service:

```
mvn -DcurrentUser=test@test.com exec:java -Dexec.mainClass="com.angelogalvao.samples.GetAllUsersExample"
```
