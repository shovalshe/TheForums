/*
This interface provides the methods to retrieve all users' data.
 */

package com.forumsServer.service;

import com.auth0.client.mgmt.UsersEntity;
import com.auth0.json.mgmt.users.UsersPage;

import java.io.IOException;

public interface UsersService {
    UsersPage getUsers() throws IOException;
}
