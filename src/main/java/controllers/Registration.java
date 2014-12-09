package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import tools.Factory;
import tools.SecuritySettings;

@Path("/")
@PermitAll
public class Registration {

    public static final int PARAM_ROLE_ID = 3;  // 1- Admin, 2 - Moderator, 3 - Driver, 4 - Unpaid

    @POST
	@Path("/registration")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response registerNewUser(@Context HttpServletRequest hsr,
			@Context HttpServletResponse rspn, String data) {

		JSONObject registrationData = new JSONObject(data);
		User user = new User();
		user.setFirstname(registrationData.getString("Name"));
		user.setSurname(registrationData.getString("LastName"));
		user.setEmail(registrationData.getString("email"));
		user.setPassword(SecuritySettings.code(registrationData.getString("pass")));
		user.setRole(Factory.getInstance().getUserRoleDAO().selectById(PARAM_ROLE_ID));
		String confirmPassword =SecuritySettings.code(registrationData.getString("passconfirm"));
		if (checkPassword(user.getPassword(), confirmPassword)
				&& checkEmail(user.getEmail())) {
			Factory.getInstance().getUserDAO().insert(user);
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
	}

	private boolean checkPassword(String password, String confirmPassword) {
		if (password == null || password.equals("")) {
			return false;
		}
		if (!password.equals(confirmPassword)) {
			return false;
		}
		return true;
	}

	private boolean checkEmail(String email) {
		if (email == null || email.equals("")) {
			return false;
		}
		Pattern emailPattern = Pattern.compile("(?<email>[\\w.]+@[\\w.]+)");
		Matcher emailMatcher = emailPattern.matcher(email);
		if (!emailMatcher.find()) {
			return false;
		}
		User user = Factory.getInstance().getUserDAO().selectByEmail(email);
		return user == null;
	}

}
