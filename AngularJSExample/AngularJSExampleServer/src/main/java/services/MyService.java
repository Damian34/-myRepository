/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import table.User;
import table.Message;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import operation.UserOperation;

@Path("MyServices")
@Stateless
public class MyService {

    private UserOperation userOperation = new UserOperation();

    @GET
    @Path("userListJson")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getListJson() {
        List<User> users = Arrays.asList(new User("user1", "pass1"), new User("user2", "pass2"));
        return users;
    }

    @GET
    @Path("getHelloJson")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHelloJson() {
        return "Hello from REST sever";
    }

    @POST
    @Path("repeatJson")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String repeatJson(Message text) {
        String message = "napisałeś: " + text.message;
        return new Message(message).toString();
    }

    @POST
    @Path("createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(User user) {
        return userOperation.createUser(user);
    }

    @POST
    @Path("updateNote")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateNote(User user) {
        return userOperation.updateNote(user);
    }

    @POST
    @Path("findUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String findUser(User user) {
        return userOperation.findUser(user);
    }
}
