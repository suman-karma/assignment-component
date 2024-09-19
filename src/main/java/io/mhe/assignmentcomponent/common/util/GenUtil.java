/*
 * $Source$
 * $Revision$ $Date$ $Author$ $Date$ GenUtil.java
 * Copyright 2001 The McGraw-Hill Companies. All Rights Reserved Created on Jul 6, 2003, 4:55:19 PM by Kameshwar.
 */

package io.mhe.assignmentcomponent.common.util;


import io.mhe.assignmentcomponent.service.AssignmentCopyService;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kameshwar
 * @author yogesh_yadav
 * 
 */
public final class GenUtil {
	private static final Logger logger = LoggerFactory.getLogger(GenUtil.class);

	public static Date getDefaultDate(String timezone, boolean startDate) {
		String configDate = null;
		Date defaultDate = null;
		if (startDate) {
			configDate = System.getProperty("hm.assignment.default.startdate");
		}
		else {
			configDate = System.getProperty("hm.assignment.default.enddate");
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			TimeZone tz = DateUtil.DEFAULT_TIMEZONE;
			formatter.setTimeZone(tz);
			defaultDate = formatter.parse(configDate);
			defaultDate = DateUtil.format(defaultDate, timezone);
			if (logger.isDebugEnabled()) {
				logger.debug("Default date = " + defaultDate);
			}
		} catch (Exception e) {
			logger.error("Exception in setting defaultDate " + e);
		}
		return defaultDate;
	}

	public static Long parseLong(Object obj, long defaultValue) {
		long returnValue = defaultValue;
		if (obj != null) {
			if (obj instanceof Long) {
				returnValue = (Long) obj;
			} else if (obj instanceof Float) {
				returnValue = ((Float) obj).longValue();
			} else if (obj instanceof Double) {
				returnValue = ((Double) obj).longValue();
			} else if (GenUtil.isValidLong("" + obj)) {
				returnValue = Long.parseLong("" + obj);
			} else if (GenUtil.isValidDouble("" + obj)) {
				returnValue = parseLong(Double.parseDouble(("" + obj).trim()), defaultValue);
			} else if (GenUtil.isValidFloat("" + obj)) {
				returnValue = parseLong(Float.parseFloat(("" + obj).trim()), defaultValue);
			} else if (GenUtil.isValidInt("" + obj)) {
				returnValue = parseLong(Integer.parseInt(("" + obj).trim()), defaultValue);
			}
		}
		return returnValue;
	}

	public static boolean isValidLong(String str) {
		try {
			Long.parseLong(str.trim());
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	public static boolean isValidDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	public static boolean isValidInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException ne) {
			return false;
		}
	}

	public static boolean isValidFloat(String str) {
		try {
			Float.parseFloat(str.trim());
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	public static boolean isBlankString(String str) {
        return str == null || "".equals(str.trim()) || str.equals("null");
    }

	public static boolean isBlankArray(Object[] arr) {
        return arr == null || arr.length == 0;
    }

	public static boolean isNull(Collection<?> collection) {
		return !(collection != null && collection.size() > 0);
	}

	public static String getStackTrace(Throwable ex) {
		StringBuilder errStr = new StringBuilder();
		if (ex != null) {
			errStr.append("[Exception Type {" + ex.getClass().getName() + "}]\n");
			errStr.append("[Stack Trace Message{" + ex.getMessage() + "}]");
			StringWriter writer = new StringWriter();
			ex.printStackTrace(new PrintWriter(writer));
			errStr.append("[Stack Trace { " + writer + " } ]");
		}
		return errStr.toString();
	}
}
