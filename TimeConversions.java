import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeConversions {

	static String[] monthFull = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	static String[] monthAbr = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	static String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	static String[] daysAbr = { "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun" };

	// 5 by 5 character display for each digit
	static String[][] blockDisplays = { { "#####", "#   #", "#   #", "#   #", "#####" }, // 0
			{ "    #", "    #", "    #", "    #", "    #" }, // 1
			{ "#####", "    #", "#####", "#    ", "#####" }, // 2
			{ "#####", "    #", "#####", "    #", "#####" }, // 3
			{ "#   #", "#   #", "#####", "    #", "    #" }, // 4
			{ "#####", "#    ", "#####", "    #", "#####" }, // 5
			{ "#####", "#    ", "#####", "#   #", "#####" }, // 6
			{ "#####", "    #", "    #", "    #", "    #" }, // 7
			{ "#####", "#   #", "#####", "#   #", "#####" }, // 8
			{ "#####", "#   #", "#####", "    #", "#####" }, // 9
			{ " ", ".", " ", ".", " " } // :
	};

	// displays time as ASCII art block letters (5 by 5)
	// time is in format HH:mm
	// uses blockDisplays matrix
	public static void displayBlock(String time) {
		for (int row = 0; row < blockDisplays[0].length; row++) {
			for (int i = 0; i < time.length(); i++) { // loops through each character of string time
				char c = time.charAt(i);
				int index = c - 48;
				if (c == '.')
					index = 10;
				System.out.print(blockDisplays[index][row] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {

		// Use Java Date Formatter
		String[] possibleFormats = { "MM/dd/yyyy HH:mm", // years through minutes
				"yyyy-MM-dd HH:mm:ss aa", // years through seconds with AM/PM
				"yyyy-MMMM-dd HH:mm:ss aa", // same as previous except month is full (not abbreviated)
				"MMMM dd, yyyy - hh:mm aa", // years through minutes with full month and AM/PM
				"HH:mm:ss", // hours through seconds
				"HH:mm", // hours through minutes
				"mm:ss", // minutes through seconds
				"HH:mm aa", // hours through minutes with AM/PM
				"H:mm aa", // same as previous format, except hour does not have leading zero
				"hh:mm aa", // same as previous format, except hour is in 12-hour format, not 24-hour format
				"EE yyyy-MMMM-dd HH:mm:ss aa", // includes abbreviated day of week "EE"
				"EEEE yyyy-MMMM-dd HH:mm:ss aa", // includes full day of week "EEEE"
		};

		// EXAMPLE #1: Manipulating date with Java Calendar
		// today's date
		SimpleDateFormat dateFormat = new SimpleDateFormat("EE yyyy-MM-dd HH:mm:ss a");
		Date date = new Date();

		// manipulate date
		System.out.println("Current Date: " + dateFormat.format(date));

		// Convert Date to Calendar
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		// Perform addition/subtraction
		c.add(Calendar.YEAR, 2);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -10);
		c.add(Calendar.HOUR, -4);
		c.add(Calendar.MINUTE, 30);
		c.add(Calendar.SECOND, 50);

		// Convert calendar back to Date
		// gets formatted version of updated date
		System.out.println("Updated Date: " + dateFormat.format(c.getTime()));
		// gets non-formatted version of updated date
		System.out.println("Full Updated Date: " + c.getTime()); // includes day of week

		// EXAMPLE #2: Comparing two dates to see which one is larger
		// arbitrary time (minutes through seconds)
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date2 = new Date();
		try {
			date2 = dateFormat2.parse("2000-12-24 12:54:02");
		} catch (ParseException e) {
			System.out.println("Unparseable using" + dateFormat);
		}
		// compare this date to earlier date
		if(date2.compareTo(date) > 0) { // date2 is larger than date
			System.out.println("'" + date2 + "' is larger than '" + date + "'");
		} else if(date2.compareTo(date) < 0) { // date2 is smaller than date
			System.out.println("'" + date2 + "' is smaller than '" + date + "'");
		} else if(date2.compareTo(date) == 0) { // date2 is the same date as date
			System.out.println("'" + date2 + "' is the same date as '" + date + "'");
		}
		
		// EXAMPLE #3: Getting the amount of time between two dates
		long diffInMillies = Math.abs(date2.getTime() - date.getTime());
		long diffInSecs = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffInMins = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffInHours= TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		System.out.println("'" + date2 + "' and '" + date + "' are " + diffInSecs + " seconds apart!");
		System.out.println("'" + date2 + "' and '" + date + "' are " + diffInMins + " minutes apart!");
		System.out.println("'" + date2 + "' and '" + date + "' are " + diffInHours + " hours apart!");
		System.out.println("'" + date2 + "' and '" + date + "' are " + diffInDays + " days apart!");
		
		// display block time
		// for an actual reference problem using this method,
		// check out "Practice Contest 3-19-2022" >> "p10.java"
		displayBlock("16:59");
	}
}
