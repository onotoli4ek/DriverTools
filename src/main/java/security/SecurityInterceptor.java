package security;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import model.entity.Session;
import model.entity.User;
import controllers.auth.MySecurityContext;
import tools.Factory;

/**
 * This interceptor verify the access permissions for a user based on sessionID
 * stored in cookie
 * */
@Provider
public class SecurityInterceptor implements
		javax.ws.rs.container.ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext request) {
		User user = null;
		Session session = null;
		// Get session id from request header
		java.util.Map<String, Cookie> cookies = request.getCookies();
		if (cookies != null) {
			Cookie sessionCookie = cookies.get("sessionUID");
			if (sessionCookie != null) {
				final String sessionId = sessionCookie.getValue();
				if (sessionId != null && sessionId.length() > 0) {
					// Load session object from repository
					session = Factory.getInstance().getSessionDAO()
							.selectBySessionId(sessionId);
					// Load associated user from session
					if (null != session) {
						checkRoleAllowed(request, session);
						session.setLastAccessedTime(new Timestamp(Calendar
								.getInstance().getTime().getTime()));
						Factory.getInstance().getSessionDAO().update(session);
						user = session.getUser();
					} else {
						request.abortWith(ACCESS_DENIED);
					}
				}
			}
		}
		// Set security context
		request.setSecurityContext(new MySecurityContext(session, user));
	}

	private static final ServerResponse ACCESS_DENIED = new ServerResponse(
			"Access denied for this resource", 401, new Headers<Object>());;
	private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(
			"Nobody can access this resource", 403, new Headers<Object>());;
	private static final ServerResponse SERVER_ERROR = new ServerResponse(
			"INTERNAL SERVER ERROR", 500, new Headers<Object>());;

	private void checkRoleAllowed(ContainerRequestContext request,
			Session session) {
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) request
				.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		checkAllowedForAll(method);
		checkDenyAll(request, method);
		checkRolesAllowed(request, session, method);
	}

	private void checkAllowedForAll(Method method) {
		if (method.isAnnotationPresent(PermitAll.class)) {

		}
	}

	private void checkDenyAll(ContainerRequestContext request, Method method) {
		if (method.isAnnotationPresent(DenyAll.class)) {
			request.abortWith(ACCESS_FORBIDDEN);
		}
	}

	private void checkRolesAllowed(ContainerRequestContext request,
			Session session, Method method) {
		if (method.isAnnotationPresent(RolesAllowed.class)) {
			RolesAllowed rolesAnnotation = method
					.getAnnotation(RolesAllowed.class);
			Set<String> rolesSet = new HashSet<String>(
					Arrays.asList(rolesAnnotation.value()));
			// Is user valid?
			if (!isUserAllowed(session.getUser(), rolesSet)) {
				request.abortWith(ACCESS_DENIED);
			}
		}
	}

	private boolean isUserAllowed(User user, Set<String> roleSet) {
		if (user == null)
			return false;
		return roleSet.contains(user.getRole().getName());
	}
}