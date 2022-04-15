/*
 * Time-line Implementation:
 * Implementation includes hours and minutes
 * Find the optimal subset of non-overlapping times
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Timeline {
	public static void main(String[] args) {
		Scanner f = new Scanner(
				"6\n[17:00, 22:30] [01:45, 03:33] [01:00, 02:50] [01:45, 03:00] [02:45, 03:01] [11:05, 11:09]");
		
		// convert string into array of events
		Event[] events = inputFormat(f);
		
		// sort events by start time
		Arrays.sort(events);
		
		// remove events that overlap
		ArrayList<Event> sol = removeConflicts(events);

		// print out remaining events
		for (Event e : sol)
			System.out.print(e + " ");
		System.out.println();
	}

	// select subset of non-overlapping times
	// times that end earlier are preferable if two times are in the same block
	public static ArrayList<Event> removeConflicts(Event[] events) {
		ArrayList<Event> sol = new ArrayList<Event>();
		sol.add(events[0]);
		for (int i = 1; i < events.length; i++) {
			if ((sol.get(sol.size() - 1).endH * 60 + sol.get(sol.size() - 1).endM) < (events[i].startH * 60
					+ events[i].startM))
				sol.add(events[i]);
		}
		return sol;
	}

	// process input
	public static Event[] inputFormat(Scanner f) {
		int dataSize = f.nextInt();
		f.nextLine();
		Event[] events = new Event[dataSize];
		while (dataSize-- > 0) {
			String start = f.next();
			start = start.substring(1, start.length() - 1);
			String end = f.next();
			end = end.substring(0, end.length() - 1);
			Event e = new Event(Integer.parseInt(start.substring(0, start.indexOf(":"))),
					Integer.parseInt(start.substring(start.indexOf(":") + 1)),
					Integer.parseInt(end.substring(0, end.indexOf(":"))),
					Integer.parseInt(end.substring(end.indexOf(":") + 1)));
			events[dataSize] = e;
		}
		return events;
	}
}

class Event extends Object implements Comparable<Object> {
	public int startH;
	public int startM;
	public int endH;
	public int endM;

	@Override
	// sort two times by end times
	public int compareTo(Object o) {
		Event e = (Event) o;
		return (this.endH * 60 + this.endM) - (e.endH * 60 + e.endM);
	}

	public Event(int startH, int startM, int endH, int endM) {
		this.startH = startH;
		this.startM = startM;
		this.endH = endH;
		this.endM = endM;
	}

	public String toString() {
		return String.format("[%02d:%02d, %02d:%02d]", startH, startM, endH, endM);
	}
}
