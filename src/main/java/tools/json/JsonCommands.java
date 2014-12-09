package tools.json;

import controllers.auth.CookieUtil;
import model.entity.DriversCombination;
import model.entity.User;
import model.entity.WorkHours;
import org.json.JSONException;
import org.json.JSONObject;
import tools.Factory;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Anatolii
 */
public class JsonCommands {

    public static final String PARAM_FIRSTNAME = "firstname";
    public static final String PARAM_EMAIL_FORGOT = "email_forgot";
    public static final String PARAM_START_WORKING_TIME = "startWorkingTime";
    public static final String PARAM_END_WORKING_TIME = "endWorkingTime";
    public static final String PARAM_RIDE_TYPE = "rideType";
    public static final String PARAM_REST_TIME = "restTime";
    public static final String PARAM_DATE = "date";
    public static final String PARAM_CURRENT_YEAR = "currentYear";
    public static final String PARAM_CURRENT_MONTH = "currentMonth";
    public static final String PARAM_CURRENT_DAY = "currentDay";
    public static final String PARAM_DATE_PATTERN = "yyyy-MM-dd";
    public static final String PARAM_YEAR_PATTERN = "yyyy";
    public static final String PARAM_MONTH_PATTERN = "MM";
    public static final String PARAM_DAY_PATTERN = "dd";
    public static final String PARAM_TIME_PATTERN = "HH-mm";

    private JsonCommands(){}

    /**
     * return null if user not exist in session
     */
    public static JsonObject getUserHomeData (HttpServletRequest hsr) throws JsonException{
        CookieUtil cookieUtil = new CookieUtil();
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        User user = Factory
                .getInstance()
                .getSessionDAO()
                .selectBySessionId(cookieUtil.getSessionIdFromRequest(hsr))
                .getUser();
        if (user != null){
            JsonObject resultJsonDate = Json.createObjectBuilder()
                    .add(PARAM_FIRSTNAME, user.getFirstname())
                    .add(PARAM_DATE, dateFormat.format(currentDate))
                    .build();
            return resultJsonDate;

        } else {
            return  null;
        }
    }

    /**
     * return null if email not exist in database
     */
    public static User getRestoreData (String data) throws JSONException{
        JSONObject emailData = new JSONObject(data);
        User user = Factory
                .getInstance()
                .getUserDAO()
                .selectByEmail(emailData.getString(PARAM_EMAIL_FORGOT));
        if (user != null) {
            return user;
        } else {
            return null;
        }

    }

    /**
     * return null if any data not exist in database for this date
     */
    public static JsonArray getUserTimeData(HttpServletRequest hsr, String data) throws ParseException {
        JsonArray workHoursArray = null;
        User user = Factory
                .getInstance()
                .getSessionDAO()
                .selectBySessionId(new CookieUtil().getSessionIdFromRequest(hsr))
                .getUser();
        DateFormat sdf = new SimpleDateFormat(PARAM_DATE_PATTERN);
        Date date = sdf.parse(data);
        List<WorkHours> workHours = Factory
                .getInstance()
                .getWorkHoursDAO()
                .selectByUserAndDate(date, user);
        if (workHours != null){
            JsonArrayBuilder jsonArray = Json.createArrayBuilder();
            for (WorkHours wh : workHours){
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add(PARAM_DATE, wh.getDate().toString());
                jsonObjectBuilder.add(PARAM_START_WORKING_TIME, wh.getStartWorkingTime().toString());
                jsonObjectBuilder.add(PARAM_END_WORKING_TIME, wh.getEndWorkingTime().toString());
                jsonObjectBuilder.add(PARAM_REST_TIME, wh.getRestTime());
                jsonObjectBuilder.add(PARAM_RIDE_TYPE, wh.getDriversCombination().toString());
                jsonArray.add(jsonObjectBuilder);
            }
            workHoursArray= jsonArray.build();
        }

        if (workHoursArray != null) {
            return workHoursArray;
        } else {
            return null;
        }
    }
    /**
     * return null if any data not exist in database for this date
     */
    public static WorkHours parseTimeTab (HttpServletRequest hsr, String data) throws ParseException {
        JSONObject timeTabData = new JSONObject(data);
        WorkHours workHours = null;
        DateFormat dateFormat = null;
        DateFormat timeFormat = null;
        User user = Factory
                .getInstance()
                .getSessionDAO()
                .selectBySessionId(new CookieUtil().getSessionIdFromRequest(hsr))
                .getUser();
        if (user != null) {
            workHours = new WorkHours();
            dateFormat = new SimpleDateFormat(PARAM_DATE_PATTERN);
            timeFormat = new SimpleDateFormat(PARAM_TIME_PATTERN);

            Date date = dateFormat.parse(timeTabData.getString(PARAM_DATE));    //TODO: check actual date
            Date startWorkingTime = timeFormat.parse(timeTabData.getString(PARAM_START_WORKING_TIME));  //TODO: Check if start working time earlier then end
            Date endWorkingTime = timeFormat.parse(timeTabData.getString(PARAM_END_WORKING_TIME));

            workHours.setDriversCombination(DriversCombination.valueOf(timeTabData.getString(PARAM_RIDE_TYPE)));
            workHours.setStartWorkingTime(startWorkingTime);
            workHours.setEndWorkingTime(endWorkingTime);
            workHours.setDate(date);
            workHours.setRestTime(timeTabData.getInt(PARAM_REST_TIME));
            workHours.setIdUser(user);

            return workHours;
        } else {
            return null;
        }
    }
    /**
     * return null if user not exist in session
     */
    public static JsonArray getInitAfterLoginData (HttpServletRequest hsr) throws JsonException, ParseException {
        CookieUtil cookieUtil = new CookieUtil();
        JsonArray initDataArray = null;
        DateFormat yearFormat = null;
        DateFormat monthFormat = null;
        DateFormat dayFormat = null;
        DateFormat dateFormat = null;

        User user = Factory
                .getInstance()
                .getSessionDAO()
                .selectBySessionId(cookieUtil.getSessionIdFromRequest(hsr))
                .getUser();
        if (user != null){
            Date currentDate = new Date();
            yearFormat = new SimpleDateFormat(PARAM_YEAR_PATTERN);
            monthFormat = new SimpleDateFormat(PARAM_TIME_PATTERN);
            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add(PARAM_FIRSTNAME, user.getFirstname());
            jsonObjectBuilder.add(PARAM_DATE, dateFormat.format(currentDate));
           //         .add(PARAM_CURRENT_YEAR, yearFormat.parse(currentDate.toString()).toString());
            jsonArray.add(jsonObjectBuilder);
            initDataArray = jsonArray.build();
            return initDataArray;

        } else {
            return  null;
        }
    }


}
