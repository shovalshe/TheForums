/*
This class implements the UsersService interface to retrieve all users' data.
 */

package com.forumsServer.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersPage users;

    @Value("${auth0.domain}")
    private String auth0Domain;
    @Value("${auth0.clientId}")
    private String auth0ClientId;
    @Value("${auth0.clientSecret}")
    private String auth0ClientSecret;

    /**
     * This method is scheduled to run every minute to update the user data.
     * It retrieves the user data from Auth0.
     */
    @Scheduled(fixedDelay = 1000 * 60, initialDelay = 0)
    private void setUsers() throws IOException {
        AuthAPI authAPI = new AuthAPI(auth0Domain, auth0ClientId, auth0ClientSecret);
        AuthRequest authRequest = authAPI.requestToken("https://" + auth0Domain + "/api/v2/");
        TokenHolder holder = authRequest.execute();
        ManagementAPI mgmt = new ManagementAPI(auth0Domain, holder.getAccessToken());
        UserFilter filter = new UserFilter();
        System.out.println(
                "Scheduled call to Auth0 was executed (get users)");
        this.users = mgmt.users().list(filter).execute();
    }

    /**
     * This method returns the user data.
     * @return the user data.
     */
    @Override
    public UsersPage getUsers() throws IOException {
        return users;
    }
}
