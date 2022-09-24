/*
This controller provides http requests to the server to interact with the users service.
It defines the endpoints for the following request:
    - GET /api/user/: get all users.
 */

package com.forumsServer.controller;

import com.auth0.json.mgmt.users.UsersPage;
import com.forumsServer.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public UsersPage getAllUsers() throws IOException {
        return usersService.getUsers();
    }
}
