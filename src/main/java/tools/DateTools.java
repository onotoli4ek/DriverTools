package tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author Roman
 *
 */
public class DateTools {

	/**
	 * @param date
	 *            - for converting to String not null
	 * @param format
	 *            - format like 'yyyy-MM-dd'
	 * @return String value of Date
	 */
	public static String convertDateToString(Date date, String format) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTime jodatime = new DateTime(date);
		return jodatime.toString(formatter);
	}

	/**
	 * @param week - week of month (1..4)
	 * @param month - month of year (1..12)
	 * @param year - year (example 2014)
	 * @return List of date from start week
	 */
	public static List<Date> getDateForWeekMonthYear(int week, int month,
			int year) {
		List<Date> result = new ArrayList();
		DateTime dt = new DateTime(year, month, 1, 0, 0);
		dt = dt.withWeekOfWeekyear(dt.getWeekOfWeekyear() + week - 1).withDayOfWeek(1);
		result.add(dt.toDate());
		System.out.println("convert : "
				+ convertDateToString(dt.toDate(), "dd-MM-yyyy"));
		for (int i = 1; i <= 6; i++) {
			result.add(dt.plusDays(i).toDate());
			System.out
					.println("convert : "
							+ convertDateToString(dt.plusDays(i).toDate(),
									"dd-MM-yyyy"));
		}
		return result;
	}

}
