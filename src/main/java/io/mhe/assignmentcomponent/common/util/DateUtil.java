package io.mhe.assignmentcomponent.common.util;

import io.mhe.assignmentcomponent.vo.Assignment;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.joda.time.DateTimeConstants.*;

public abstract class DateUtil {

	public static final String DB_TIMEZONE_ID = "US/Eastern";
	public static final String UTC_TIMEZONE_ID = "UTC";

	public static final String DATE_FORMAT_IN_UTC_WITH_OFF_SET = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone(DB_TIMEZONE_ID);

    public static final int NO_OF_SECS_IN_DAY = 24 * 60 * 60;
    public static final int NO_OF_SECS_IN_HOUR = 60 * 60;
    public static final int NO_OF_SECS_IN_MIN = 60;
    public static final int NO_OF_MINUTES_IN_HOUR = 60;
    
    public static final int NO_OF_MILLI_SECS_IN_DAY = 24 * 60 *60 * 1000;
    public static final int NO_OF_MILLI_SECS_IN_HOUR = 60 * 60 * 1000;
    public static final int NO_OF_MILLI_SECS_IN_MIN = 60 * 1000;
    
    public static final Date INFINITE_YEAR = getInfiniteYear();
    public static final int END_OF_DAY_HOUR = 23;
    public static final int END_OF_DAY_MINUTE = 59;
    public static final int END_OF_DAY_SECOND = 59;
    public static final int END_OF_DAY_MILLISECOND = 999;

	public static float getRawTimeZoneOffset(String timeZone)
	{
		if (Assignment.stringIsBlankOrNull(timeZone))
		{
			timeZone = "UTC";
		}
		TimeZone defaultTimeZone = TimeZone.getTimeZone("UTC");
		TimeZone contextTimeZone = TimeZone.getTimeZone(timeZone);
		return (float) (defaultTimeZone.getRawOffset() + contextTimeZone.getRawOffset() + defaultTimeZone.getDSTSavings() + contextTimeZone
				.getDSTSavings())
				/ (SECONDS_PER_MINUTE * MILLIS_PER_SECOND * MINUTES_PER_HOUR);

	}

    public enum DATE_FORMAT {
		DATE_FORMAT_MMM_DD_YYYY_HH_MM_SS_A("MMM dd, yyyy hh:mm:ss a"),
		DATE_FORMAT_MM_DD_YY_HH_MM_A("MM/dd/yy hh:mm a"), 
		DATE_FORMAT_MMM_DD_YYYY("MMM dd, yyyy");
		
		private String format;
		private DATE_FORMAT(String format){
			this.format = format;
		}
		public String getFormat() {
			return format;
		}
	}

    public static enum TIME_UNIT {
    	DAY("days"), HOUR("hours"), MINUTE("minutes"), SECOND("seconds");

		TIME_UNIT(String str) {
		    this.str = str;
		}

		private String str;

		public String getKey() {
		    return str;
		}
    }

    /**
     * Method to get duration in days
     * @param timeInMilliSeconds
     * @return
     */
    public static double getDurationInDays(float timeInMilliSeconds){
    	return timeInMilliSeconds/(double)NO_OF_MILLI_SECS_IN_DAY;
    }
    
    /**
     * Method to get duration in hours
     * @param timeInMilliSeconds
     * @return
     */
    public static double getDurationInHours(float timeInMilliSeconds){
    	return timeInMilliSeconds/(double)NO_OF_MILLI_SECS_IN_HOUR;
    }

    public static Date getInfiniteYear(){
    	try {
    		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT.DATE_FORMAT_MMM_DD_YYYY.getFormat());
    		return sdf.parse("Jan 01, 2100");
		} catch (ParseException e) {
			return null;
		}
    }
    
    public static boolean isInfiniteTime(Date date){
    	return date.getTime() >= (INFINITE_YEAR.getTime() - NO_OF_MILLI_SECS_IN_DAY);
    }

	public static Date format(Date date, String timezoneId) {

		final DateTime dt = new DateTime(date);
		return dt.toDateTime(DateTimeSupport.timeZoneForID(timezoneId)).toDate();
	}

	@Deprecated
	public static String convertBetweenTimeZoneAndPattern(String dateStr, String fromTimeZone, String toTimeZone, String fromPattern, String toPattern) {
		if (dateStr == null) {
			return null;
		}
		return DateTimeSupport.convertBetweenTimeZoneAndPattern(dateStr, fromTimeZone, toTimeZone, fromPattern, toPattern);
	}
}
