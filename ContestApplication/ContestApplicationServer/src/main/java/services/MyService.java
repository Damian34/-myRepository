/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import message.*;
import operation.*;
import tables.Competition;

@Path("service")
@Stateless
public class MyService {

    private UserOperation userOperation = new UserOperation();
    private CompetitionOperation competitionOperation = new CompetitionOperation();
    private ParticipationOperation participationOperation = new ParticipationOperation();

    @POST
    @Path("createAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createAccount(UserSimple user) {
        return new Message(userOperation.createAccount(user)).toString();
    }

    @POST
    @Path("logIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String LogIn(UserSimple user) {
        return new Message(userOperation.getLoggedUser(user)).toString();
    }

    @POST
    @Path("getRole")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getRole(UserSimple user) {
        return new Message(userOperation.getRole(user)).toString();
    }

    @POST
    @Path("createCompetition")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createCompetition(UserCompetition userCompetition) {
        return new Message(competitionOperation.createCompetition(userCompetition)).toString();
    }

    @GET
    @Path("getToDayCompetition")
    @Produces(MediaType.APPLICATION_JSON)
    public CompetitionMessage getToDayCompetition() {
        Competition competition = competitionOperation.getToDayCompetition();
        if (competition == null) {
            return new CompetitionMessage(0, "", "", "");
        }
        return new CompetitionMessage(competitionOperation.getToDayCompetition());
    }

    @POST
    @Path("takePart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String takePart(UserSimple user) {
        return new Message(participationOperation.takePart(user)).toString();
    }

    @GET
    @Path("getAllCompetitions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CompetitionMessage> getAllCompetitions() {
        return competitionOperation.getAllCompetitions();
    }

    @POST
    @Path("getUsersParticipation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsersParticipation(Message msg) {
        return participationOperation.getUsersParticipation(msg.message).toString();
    }

    @POST
    @Path("selectWinners")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String selectWinners(WinnersMessage winners) {
        return new Message(participationOperation.selectWinners(winners)).toString();
    }

    @POST
    @Path("getCurrentWinners")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public WinnersMessage getCurrentWinners(Message idCompetition) {
        return participationOperation.getCurrentWinners(idCompetition.message);
    }

    @GET
    @Path("getAllWinners")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WinnersMessage> getAllWinners() {
        return participationOperation.getAllWinners();
    }

    /////////////////////////////////////////////////
    //test methods:
    @GET
    @Path("getHelloJson")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHelloJson() {
        return new Message("Hello from REST sever").toString();
    }
    
    @GET
    @Path("getHello")
    @Produces(MediaType.TEXT_HTML)
    public String getHello() {
        return "Hello from REST sever";
    }
}
