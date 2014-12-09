package controllers;

import model.entity.WorkHours;
import tools.Factory;
import tools.json.JsonCommands;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

/**
 * @author Anatolii
 */
@Path("/")
@PermitAll
public class WeeklyRouteInfo {
    @RolesAllowed({ "Driver" })
    @POST
    @Path("/time")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Response getTimeInfo(@Context HttpServletRequest hsr, String data) throws ParseException {
        WorkHours workHours = JsonCommands.parseTimeTab(hsr, data);
        if (workHours != null && Factory.getInstance().getWorkHoursDAO().insert(workHours)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }

    @RolesAllowed({ "Driver" })
    @POST
    @Path("/time2")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getTimeInfo2(@Context HttpServletRequest hsr) throws ParseException {
        JsonArray jsonData = JsonCommands.getUserTimeData(hsr, "2014-12-07");   //TODO: date -  will be string from front-end (Week number)
        if (jsonData != null){
            return Response.ok(jsonData).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
