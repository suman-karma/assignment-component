package io.mhe.assignmentcomponent.common.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * An utility class to work with dates using JodaTime.
 */
public abstract class DateTimeSupport {
    // General constansts
    public static final int MILLISECONDS_IN_SECOND = 1000;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int MILLISECONDS_IN_MINUTE = SECONDS_IN_MINUTE * MILLISECONDS_IN_SECOND;
    public static final int SECONDS_IN_HOUR =
            MINUTES_IN_HOUR * SECONDS_IN_MINUTE;
    public static final int MIILLISECONDS_IN_HOUR = SECONDS_IN_HOUR *
            MILLISECONDS_IN_MINUTE;
    public static final int MINUTES_IN_DAY = HOURS_IN_DAY * MINUTES_IN_HOUR;
    public static int SECONDS_IN_DAY = MINUTES_IN_DAY * SECONDS_IN_MINUTE;
    public static int MILLISECONDS_IN_DAY = SECONDS_IN_DAY
            * MILLISECONDS_IN_SECOND;

    // Default date format patterns
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'VV'";
    public static final String EXTENDED_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'VV'";
    public static final String SIMPLE_DATE_FORMAT = "MM/dd/yyyy hh:mm a";
    public static final String DEFAULT_TIMEZONE = "US/Eastern";
    public static final String UTC_TIMEZONE = "UTC";
	public static final String MM_DD_YYYY_SLASH_DELEMITED = "MM/dd/yyyy";
	public static final String DEFAULT_TIME_AND_TIMEZONE_FOR_DELAY_FEEDBACK_DATE = "12:00 am EST";
	public static final int MILINS_IN_HOUR_FOR_DELAYED_FEEDBACK=MILLISECONDS_IN_SECOND*SECONDS_IN_MINUTE*MINUTES_IN_HOUR;
	
	
	private static final DateTimeFormatter ISO_8601_DATE_FORMATTER = ISODateTimeFormat.dateTime();

    private static volatile List<String> supportedTimezones;

    public static final List<String> unsupportedTimezones = 
            Arrays.asList("SystemV/HST10",
            "SystemV/YST9",
            "SystemV/YST9YDT",
            "SystemV/PST8",
            "SystemV/PST8PDT",
            "SystemV/MST7",
            "SystemV/MST7MDT",
            "SystemV/CST6",
            "SystemV/CST6CDT",
            "SystemV/EST5",
            "SystemV/EST5EDT",
            "SystemV/AST4",
            "SystemV/AST4ADT",
            "Asia/Riyadh87",
            "Asia/Riyadh88",
            "Asia/Riyadh89",
            "Mideast/Riyadh87",
            "Mideast/Riyadh88",
            "Mideast/Riyadh89");

    public static DateTimeFormatter getDateTimeFormatter(final String pattern, final String timezone) {
        return DateTimeFormat.forPattern(pattern).withZone(timeZoneForID(timezone));
    }

    public static DateTimeZone timeZoneForID(final String timezoneID) {
        try {
            return DateTimeZone.forID(timezoneID);
        } catch (final Exception e) {
            return DateTimeZone.forTimeZone(TimeZone.getTimeZone(timezoneID));
        }
    }

    public static String convertToString(final DateTime dateTime, final String format, final String timeZone) {
        return getDateTimeFormatter(format, timeZone).print(dateTime);
    }

    public static String convertToString(final DateTime dateTime, final String format) {
        return convertToString(dateTime, format, DEFAULT_TIMEZONE);
    }

    public static DateTime convertToDateTime(final String strDate, final String format, final String timeZone) {
        return getDateTimeFormatter(format, timeZone).parseDateTime(strDate.trim());
    }

    public static DateTime convertToDateTime(final String strDate, final String format) {
        return convertToDateTime(strDate, format, DEFAULT_TIMEZONE);
    }

    public static String convertToString(final Date date, final String format, final String timeZone) {
        return convertToString(new DateTime(date), format, timeZone);
    }

    public static String convertToString(final Date date, final String format) {
        return convertToString(date, format, DEFAULT_TIMEZONE);
    }
    
    public static Timestamp convertToTimestamp(final Date date) {
    	DateTime dtLocal = new DateTime(date).withZone(DateTimeZone.forID(DEFAULT_TIMEZONE));
    	return new Timestamp(dtLocal.getMillis());
    }
    
    public static Timestamp convertToTimestamp(final String dateStr) {
    	DateTime dtLocal = new DateTime(dateStr).withZone(DateTimeZone.forID(DEFAULT_TIMEZONE));
    	return new Timestamp(dtLocal.getMillis());
    }

    public static Date getPastDate(final Date date, int days) {
    	return new DateTime(date).withZone(DateTimeZone.forID(DEFAULT_TIMEZONE)).minusDays(days).toDate();
    }

    public static Date getDateInDefaultTimeZone(final Date date) {
    	return new DateTime(date).withZone(DateTimeZone.forID(DEFAULT_TIMEZONE)).toDate();
    }

    public static Date convertToDate(final String strDate, final String format, final String timeZone) {
        return convertToDateTime(strDate, format, timeZone).toDate();
    }

    public static Date convertToDate(final String strDate, final String format) {
        return convertToDateTime(strDate, format, DEFAULT_TIMEZONE).toDate();
    }

    public static double hoursOffsetFromUTC(final String timezone) {
        DateTime nowHere = DateTime.now();

        DateTime utcDateTime = nowHere.withZone(timeZoneForID("UTC"));
        DateTime inputDateTime = nowHere.withZone(timeZoneForID(timezone));

        final Period period = new Period(inputDateTime.toLocalDateTime(), utcDateTime.toLocalDateTime());

        return period.toStandardSeconds().getSeconds() / (SECONDS_IN_HOUR * 1.0f);
    }

    public static String getTimezoneDisplayName(String timezoneID) {
        // TODO : do those conversion using standard Joda methods (?)
        String offset = getDateTimeFormatter("ZZ", timezoneID).print(new DateTime().withZone(timeZoneForID("GMT")));
        return "GMT" + offset;
    }

    public static String convertBetweenTimeZoneAndPattern(String dateStr, String fromTimeZone, String toTimeZone, String fromPattern, String toPattern) {
        if (dateStr == null) {
            return null;
        }
        return DateTimeSupport.convertToString(DateTimeSupport.convertToDateTime(dateStr, fromPattern, fromTimeZone), toPattern, toTimeZone);
    }

    public static List<String> getSupportedTimezones() {

        if (supportedTimezones == null) {
            synchronized (DateTimeSupport.class) {
                if (supportedTimezones == null) {
                    supportedTimezones = generateSupportedTimezones();
                }
            }
        }
        return supportedTimezones;
    }

    public static List<String> getUnsupportedTimezones() {
        return unsupportedTimezones;
    }

    private static List<String> generateSupportedTimezones() {

        final List<String> supportedTZs = new ArrayList<String>();
        for (final String tz : TimeZone.getAvailableIDs()) {
            supportedTZs.add(tz);
        }

        supportedTZs.removeAll(unsupportedTimezones);

        return supportedTZs;
    }

    public static Date cloneDate(Date date){
		if(null != date){
			return (Date)date.clone();
		}else{
			return null;
		}
	}
    
    
    public static String getISO8601Formatted(final DateTime date) {
        return date != null ? ISO_8601_DATE_FORMATTER.print(date) : null;
    }

	public static String getISO8601Formatted(final Date date) {
		return date != null ? ISO_8601_DATE_FORMATTER.print(new DateTime(date)) : null;
	}
	
	public static Date convertDateFromUTCToLocal(final String dateStr) throws ParseException {
		DateFormat utcFormat = new SimpleDateFormat(EXTENDED_DATE_FORMAT);
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date utcDate = utcFormat.parse(dateStr);
		return convertToTimestamp(utcDate);
	}
	
	/**
	 * @author Sayan.Halder@mheducation.com
	 * @param sourceDateStr
	 * @param sourceDateTimeFormat
	 * @param sourceTimeZoneId
	 * @param targetDateTimeFormat
	 * @param targetTimeZoneId
	 * @return String
	 */
	public static String convertDateStringToTargetTimezoneNFormat(String sourceDateStr, String sourceDateTimeFormat,
			String sourceTimeZoneId, String targetDateTimeFormat, String targetTimeZoneId) {
		// Create a DateTimeFormatter for the input format
		DateTimeFormatter inputFormatter = DateTimeFormat.forPattern(sourceDateTimeFormat);

		// Parse the input date-time string to a DateTime object
		DateTime dateTime = DateTime.parse(sourceDateStr, inputFormatter)
				.withZoneRetainFields(DateTimeZone.forID(sourceTimeZoneId));

		// Set the desired timezone
		dateTime = dateTime.withZone(DateTimeZone.forID(targetTimeZoneId));

		// Create a DateTimeFormatter for the output format in 12-hour format
		DateTimeFormatter outputFormatter = DateTimeFormat.forPattern(targetDateTimeFormat);

		// Format the DateTime object to the desired 12-hour format with AM/PM indicator
		String outputDateTimeString = dateTime.toString(outputFormatter);

		return outputDateTimeString;
	}
	
	public static boolean compareCurrentTimeToTargetTime(String targetDateTime, String targetDateTimeFormat) {
		
		//Set the desired timezone
		DateTimeZone dtInstant = DateTimeZone.forID(DEFAULT_TIMEZONE);
		
		//Create a DateTime object to convert it to milliseconds
		DateTime dateTimeInstant = new DateTime(dtInstant);
		
		//Get the current time in milliseconds
		Long instantMilli = dateTimeInstant.getMillis();
		
		//Set the desired time format
		DateTimeFormatter formatter = DateTimeFormat.forPattern(targetDateTimeFormat);
		
		//Create a DateTime Object to get the time in milliseconds
		DateTime dateTimeTarget = formatter.parseDateTime(targetDateTime);
		
		//Get the desired time in milliseconds
		Long targetDateTimeMilli = dateTimeTarget.getMillis();
		
		//Compare the Times
		int diff = Long.compare(targetDateTimeMilli, instantMilli);
		
		//return accordingly
		boolean greaterThanCurrentTime = true;
		if(diff < 0) {
			greaterThanCurrentTime = false;
		}else {
			greaterThanCurrentTime = true;
		}
		return greaterThanCurrentTime;
	}
	
	/**
	 * @param futureYear
	 * @param futureMonth
	 * @param futureDay
	 * @param futureHour
	 * @param futureMinute
	 * @param futureSecond
	 * @return futre date object in java.util.Date type
	 */
	public static Date getFutureDateInDefaultTimeZone(int futureYear, int futureMonth, int futureDay, int futureHour, int futureMinute, int futureSecond) {
		
		DateTimeZone timeZone = DateTimeZone.forID(DEFAULT_TIMEZONE);
		
		DateTime futureDateTime = new DateTime(futureYear, futureMonth, futureDay, futureHour, futureMinute, futureSecond, timeZone);
		
		// Convert to java.util.Date
        Date futureDate = futureDateTime.toDate();
        
        return futureDate;
	}
	
	public static Date getFutureDateInDefaultTimeZone(int numberOfDaysToAdd) {
		
		DateTimeZone timeZone = DateTimeZone.forID(DEFAULT_TIMEZONE);
		
        DateTime now = DateTime.now(timeZone);
        
        // Add the specified number of days to the current date
        DateTime futureDateTime = now.plusDays(numberOfDaysToAdd);
		
		// Convert to java.util.Date
        Date futureDate = futureDateTime.toDate();
        
        return futureDate;
	}
	
	public static boolean checkIfDummyDateInDefaultTimeZone(String date, String dateFormat, boolean isStartDate) {
		Date defaultEndDate = GenUtil.getDefaultDate(DEFAULT_TIMEZONE, isStartDate);
		Date inputDate = convertToDate(date, dateFormat);
		if( inputDate.equals(defaultEndDate) ) {
			return true;
		}
		return false;
	}
	
	public static Date convertDateStringToDateWithoutTimezone(String sourceDateStr, String sourceDateTimeFormat) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(sourceDateTimeFormat);
        
        DateTime dateTime = formatter.parseDateTime(sourceDateStr);
        
        return dateTime.toDate();
	}
	
	public static String convertDateToString(Date sourceDate, String sourceDateTimeFormat) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(sourceDateTimeFormat);
		DateTime dateTime = new DateTime(sourceDate);
		
		return formatter.print(dateTime);
		
	}
}
