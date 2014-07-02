package utils.helpers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import play.Play;

/**
 * 
 * @author donatien
 * 
 *         Utility class reserved for static values and other helper methods
 */

public class Utils {
	// Path to the core application
	public static final String INTERNAL_ERROR_INVILID_PARAMS = Play.configuration
			.getProperty("internal.invilid.params.errorr");
	public static final String INTERNAL_PROCESSING_ERROR = Play.configuration
			.getProperty("internal.processing.error");
	public static final String ERROR_PACKAGE = Play.configuration
			.getProperty("internal.error.pkg");

	/**
	 * Test if something is an int number
	 * 
	 * @param anyValue
	 *            value to test
	 * @return boolean test result
	 */
	public static boolean isInt(String anyValue) {
		try {
			Integer.parseInt(anyValue);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Test if something is a long number
	 * 
	 * @param anyValue
	 *            value to test
	 * @return boolean test result
	 */
	public static boolean isLong(String longNumber) {
		try {
			Long.parseLong(longNumber);
			return true;

		} catch (Exception e) {
			// TODO: handle exception

			return false;
		}

	}

	/**
	 * Test if something is a double number
	 * 
	 * @param anyValue
	 *            value to test
	 * @return boolean test result
	 */
	public static boolean isDouble(String doubleNumber) {
		try {
			Double.parseDouble(doubleNumber);
			return true;
		} catch (Exception e) {

			return false;
		}

	}

	/**
	 * Test if something is a boolean value
	 * 
	 * @param anyValue
	 *            value to test
	 * @return boolean test result
	 */
	public static boolean isBool(String bool) {
		try {
			Boolean.parseBoolean(bool);
			return true;

		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Subtracts any number from the gate input
	 * 
	 * @param dateInAdvence
	 *            the date input
	 * @param positiveAdvencedDays
	 *            the number input to subtract from the date
	 * @return dateDiff the date reduced of positiveAdvencedDays
	 */
	public static String datesAndDaysDiff(Date dateInAdvence,
			int positiveAdvencedDays) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateDiff = null;

		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(dateInAdvence);
			cal.add(Calendar.DATE, -positiveAdvencedDays);

			dateDiff = formater.format(cal.getTime());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dateDiff;
	}

	/**
	 * Generates the hashed value of any string
	 * 
	 * @param input
	 *            any string to be hashed
	 * @return the hashed value of the input
	 */
	public static int hashCodeGenerator(String input) {
		StringBuilder builder = new StringBuilder();
		builder.append(input);
		builder.append(5);
		return builder.toString().hashCode();
	}

	/**
	 * Generates the id for any bean
	 * 
	 * @param input
	 *            form the bean description
	 * 
	 * @return anyid the generated id for the bean in question
	 */
	public static String idGenerator(String input) {

		StringBuffer buffer = new StringBuffer();
		for (int x = 0; x < 8; x++) {
			buffer.append((char) ((int) (Math.random() * 26) + 97));
		}

		return Math.abs(hashCodeGenerator(buffer + input + ""
				+ new Date().toString()))
				+ "";
	}

	public static String fourRandomNumberNenerator() {
		StringBuffer buffer = new StringBuffer();
		for (int x = 0; x < 4; x++) {
			buffer.append(((int) (Math.random() * 9) + 0));
		}
		return buffer.toString();
	}

	public static String cardNumberGenerator(String levelId) {
		return "000" + levelId + "-" + fourRandomNumberNenerator() + "-"
				+ fourRandomNumberNenerator() + "-"
				+ fourRandomNumberNenerator();
	}
	public String contructLevelId(String levelId){
		return null;
	}


}