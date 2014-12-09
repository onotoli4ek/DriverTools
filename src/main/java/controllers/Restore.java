package controllers;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.entity.User;
import org.json.JSONObject;
import tools.EmailSender;
import tools.Factory;
import tools.SecuritySettings;
import tools.json.JsonCommands;

@Path("/")
@PermitAll
public class Restore {


    @POST
	@Path("/restore")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response restorePassword(@Context HttpServletRequest hsr,
			@Context HttpServletResponse rspn, String data) {
        User user = JsonCommands.getRestoreData(data);
        if (user != null && user.getEmail() != null){
            EmailSender.sendForgotPassword(user.getFirstname(), user.getEmail(), SecuritySettings.decode(user.getPassword()));
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
	}

}
