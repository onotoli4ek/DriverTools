package controllers.auth;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.Factory;
import model.entity.Session;
import model.entity.User;


public class CookieUtil {

	 private SessionIdentifierGenerator sessionIdentifierGenerator = new SessionIdentifierGenerator();
    public String getSessionIdFromRequest(HttpServletRequest hsr) {
        if (hsr == null) return null;
        Cookie[] cookies = hsr.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("sessionUID")) {
                return c.getValue();
            }
        }
        return null;
    }

    //inserts in database and cookie
    public boolean insertSessionUID(HttpServletResponse rspn, User user) {
        String sessionUID =sessionIdentifierGenerator.nextSessionId();
        if (Factory.getInstance().getSessionDAO().insert(new Session(sessionUID, user))) {
            Cookie newCookie = new Cookie("sessionUID", sessionUID);
            rspn.addCookie(newCookie);
            return true;
        } else return false;
    }

    public boolean removeSessionUID(HttpServletRequest hsr, HttpServletResponse rspn){
        String sessionId = getSessionIdFromRequest(hsr);
        if(sessionId != null && Factory.getInstance().getSessionDAO().deleteBySessionId(sessionId)) {
            Cookie invalidateCookie = new Cookie("sessionUID", sessionId);
            invalidateCookie.setMaxAge(0);
            rspn.addCookie(invalidateCookie);
            return true;
        }
        return false;
    }
}
