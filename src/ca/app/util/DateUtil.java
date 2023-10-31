package ca.app.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {
	
	public static String getStringDate(long time) {
		return getStringDate(time, Locale.getDefault());
	}
	
	public static String getStringDate(long time, Locale locale) {
		return getStringDate(time, "MM/dd/yyyy", locale);
	}
	
	public static String getStringDate(long time, String format) {
		return getStringDate(time, format, Locale.getDefault());
	}
	
	public static String getStringDate(long time, String format, Locale locale) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
		String parsedDate = dateFormat.format(time);
		return parsedDate;
	}
	
	public static Date getDateFromString(String dateString, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date date = dateFormat.parse(dateString);
			return date;
		} catch (Throwable ignore) {
			
		}
		return null;
	}
	
	public static Date getDateFromString(String dateString) {
		return getDateFromString(dateString, "MM/dd/yyyy");
	}
	
	public static Timestamp getTimestampFromString(String dateString, String timeString, boolean startOfDay) {
		return getTimestampFromString(dateString, timeString, startOfDay, null, null);
	}
	
	public static Timestamp getTimestampFromString(String dateString, String timeString, boolean startOfDay, String dateFormat, String timeFormat) {
		String dateFormatPattern = (dateFormat==null) ? "yyyy-MM-dd'T'hh:mm:ss" : dateFormat;
		String timeFormatPattern = (timeFormat==null) ? "HH:mm" : timeFormat;
	
		if (dateString!=null) {
			Date parsedDate = DateUtil.getDateFromString(dateString,dateFormatPattern);
		
			if (parsedDate!=null) {
				Calendar calDate = Calendar.getInstance();
				calDate.setTime(parsedDate);
			
				if (timeString!=null) {
					if (timeString.length()>0) {
						try {
							DateFormat formatter = new SimpleDateFormat(timeFormatPattern);
							Date parsedTime = formatter.parse(timeString);
							Calendar calTime = Calendar.getInstance();
							calTime.setTime(parsedTime);
							calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
							calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
						} catch(Throwable ignore) {
							
						}
					} else {
						calDate.set(Calendar.HOUR_OF_DAY, (startOfDay)?0:23);
						calDate.set(Calendar.MINUTE, (startOfDay)?0:59);
						calDate.set(Calendar.SECOND, (startOfDay)?0:59);
						calDate.set(Calendar.MILLISECOND, 1); // Flag to denote that time was not set
					}
				}
				return new Timestamp(calDate.getTime().getTime());
			}
		}
		return null;
	}
	
	public static Timestamp getTimestampFromString(String dateString) {
		try {
			Date parsedDate = getDateFromString(dateString);
			Timestamp timestamp = new Timestamp(parsedDate.getTime());
			return timestamp;
		}catch(Throwable ignore) {
			
		}
		return null;
	}
	
	public static Date getDateAtMidnight(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static boolean hasTimeComponent(Timestamp timestamp) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp.getTime());
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		int ms = cal.get(Calendar.MILLISECOND);
		if (hour==0 && min==0 && sec==0 && ms==0) {
			return false;
		}
		return true;
	}
	
	public static Date incrementByDays(Date date, int numDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, numDays);
		return cal.getTime();
	}
	
	public static Date incrementByMonths(Date date, int numMonths) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, numMonths);
		return cal.getTime();
	}
	
	public static Date incrementByYears(Date date, int numYears) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, numYears);
		return cal.getTime();
	}
	
	static final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000L;
	public static long getDifferenceInDays(Date date1, Date date2) {
		date1 = getDateAtMidnight(date1);
		date2 = getDateAtMidnight(date2);
		
		return (date2.getTime() - date1.getTime())/MILLSECS_PER_DAY;
	}
	
	public static long getDifferenceInYears(Date date1, Date date2) {
		return getDifferenceInDays(date1, date2)/365;
	}
	
	public static String format(Calendar cal) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy hh:mm:ss a");
		return formatter.format(cal.getTime());
	}
	
	public static String formatTime(Timestamp timestamp) {
		SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		return formatter.format(timestamp);
	}
	
	public static String formatTime(Timestamp timestamp, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(timestamp);
	}
	
	public static String getDurationFromMillis(long millis){
		return String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}
}