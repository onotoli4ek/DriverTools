package controllers;

import tools.json.JsonCommands;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

@Path("/")
@PermitAll
public class UserInfo {

    @RolesAllowed({ "Driver" })
    @POST
    @Path("/home")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getInfo(@Context HttpServletRequest hsr ) throws JsonException, ParseException {
//        JsonObject jsonData = JsonCommands.getUserHomeData(hsr);
        JsonArray jsonData = JsonCommands.getInitAfterLoginData(hsr);
        System.out.println(jsonData);
        if (jsonData != null){
            return Response.ok(jsonData).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
