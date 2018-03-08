package sorc.Code;

import java.io.IOException;
import java.util.ArrayList;

import com.google.api.client.util.DateTime;

public class ConvertTime{

	/**
	 * convertTime This method takes the the time format from the HUB and formats it
	 * to a usable time for Google Cal. (Hope this format is usable in other cal
	 * apps.)
	 * 
	 *TODO: this needs to be cleaned up.
	 * 
	 * @param schedule
	 * @return
	 */
	public ArrayList<DateTime> convertTime(String[] schedule) {
		ArrayList<DateTime> dtList = new ArrayList<>(); // This stores the formated time
		ArrayList<String> unparsList = new ArrayList<>(); // this stores the slightly more formated time but mostly
															// unformatted.

		DateTime currentDate = new DateTime(System.currentTimeMillis()); // gets current date.
		String strCurrentDate = currentDate.toString(); // Turns the date to a string for manipulation.

		// Takes the two lines that stores the date and time and put it on one line
		// could do this when the schedule is retrieved from the HUB.
		for (int i = 0; i < schedule.length; i++) {

			unparsList.add(schedule[i] + " " + schedule[i + 1]);

			i++;// Needs to increment twice per run to skip a line.
		}

		for (String s : unparsList) {
			// vars for when the date and time is split up
			String Day = "";
			String Month = "";
			String StartTime = "";
			String EndTime = "";
			String currChar;
			String StartAMPM = "";
			String EndAMPM = "";
			
			int spaceCount = 0;
			int position = 0;
			
			// Does all the spliting. Uses the spaces as markers.
			while (position < s.length()) {

				currChar = s.substring(position, position + 1);
				if (currChar.equals(" ")) {

					s = s.substring(position);
					spaceCount++;
					position = 0;
				} else if (spaceCount == 1) {

					Month = Month + s.substring(position, position + 1);

				} else if (spaceCount == 2) {
					Day = Day + s.substring(position, position + 1);

				} else if (spaceCount == 3) {
					StartTime = StartTime + s.substring(position, position + 1);

				} else if (spaceCount == 4) {
					StartAMPM = StartAMPM + s.substring(position, position + 1);
				} else if (spaceCount == 6) {
					EndTime = EndTime + s.substring(position, position + 1);
				} else if (spaceCount == 7) {

					EndAMPM = EndAMPM + s.substring(position, position + 1);

				}

				position++;
			}
			// month and day formatting. See methods below.
			Month = monthCheck(Month);
			Day = String.format("%02d", Integer.parseInt(Day));

			// converts time to 24H time (Only does it if its PM)
			if (StartAMPM.equals("PM")) {

				StartTime = to24Hour(StartTime);

			}

			if (EndAMPM.equals("PM")) {

				EndTime = to24Hour(EndTime);

			}

			// This is for daylight savings.
			// TODO Finish this. "Works" for spring ahead. Doen't work for fall back
			// NOvember 4 is when daylight savings ends.
			// TODO this should also be its own meathod.
			if (Integer.parseInt(Month) >= 3) {
				if (Integer.parseInt(Month) == 3) {
					if (Integer.parseInt(Day) >= 11) {
						if (!StartTime.equals("Not")) {
							System.out.println("StartTime:");

							System.out.println(/* TODO:FIX this wont work at the end of the year... */strCurrentDate
									.substring(0, 4) + "-" + Month + "-" + Day + "T" + StartTime + ":00" + "-07:00");
							dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T"
									+ StartTime + ":00" + "-07:00"));
							System.out.println("StopTime:");
							System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime
									+ ":00" + "-07:00");
							dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T"
									+ EndTime + ":00" + "-07:00"));
						}
					} else {

						if (!StartTime.equals("Not")) {
							System.out.println("StartTime:");
							System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T"
									+ StartTime + ":00" + "-08:00");
							dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T"
									+ StartTime + ":00" + "-08:00"));
							System.out.println("StopTime:");
							System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime
									+ ":00" + "-08:00");
							dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T"
									+ EndTime + ":00" + "-08:00"));
						}

					}
				} else {
					if (!StartTime.equals("Not")) {
						System.out.println("StartTime:");
						System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + StartTime
								+ ":00" + "-07:00");
						dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T"
								+ StartTime + ":00" + "-07:00"));
						System.out.println("StopTime:");
						System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime
								+ ":00" + "-07:00");
						dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime
								+ ":00" + "-07:00"));
					}

				}

			} else {

				if (!StartTime.equals("Not")) {
					System.out.println("StartTime:");
					System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + StartTime
							+ ":00" + "-08:00");
					dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + StartTime
							+ ":00" + "-08:00"));
					System.out.println("StopTime:");
					System.out.println(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime + ":00"
							+ "-08:00");
					dtList.add(new DateTime(strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime
							+ ":00" + "-08:00"));
				}
			}
		}
		/**
		 * Debugging
		 */
		// for (String i : unparsList) {
		//
		// System.out.println(i);
		//
		// }
		// System.out.println(strCurrentDate);
		/**
		 * END Debugging
		 */
		return dtList;
	}
	
	/**
	 * takes the work month that was retrivied from the hub and converts it to a
	 * number.
	 * 
	 * @param M
	 * @return
	 */
	public static String monthCheck(String M) {
		String Month = "";
		// TODO Case select?
		if (M.equals("January")) {

			Month = "01";

		} else if (M.equals("February")) {
			Month = "02";

		} else if (M.equals("March")) {

			Month = "03";
		} else if (M.equals("April")) {
			Month = "04";

		} else if (M.equals("May")) {

			Month = "05";
		} else if (M.equals("June")) {
			Month = "06";

		} else if (M.equals("July")) {
			Month = "07";

		} else if (M.equals("August")) {
			Month = "08";

		} else if (M.equals("September")) {

			Month = "09";
		} else if (M.equals("October")) {

			Month = "10";
		} else if (M.equals("November")) {
			Month = "11";

		} else if (M.equals("December")) {
			Month = "12";

		}

		return Month;
	}

	/**
	 * This is the method used in convert time.
	 * 
	 * @param t
	 * @return
	 */
	public static String to24Hour(String t) {
		String s24H = "";

		int sub = 0;

		sub = Integer.parseInt(t.substring(0, 2));

		if (sub < 12) {

			sub += 12;

		}
		s24H = Integer.toString(sub) + t.substring(2);
		return s24H;
	}
	
	//publi


	/**
	 * Debugging Method.
	 */
	public ArrayList<DateTime> convertTimeTest() throws IOException {
		ArrayList<DateTime> dtList = new ArrayList<>();
		ArrayList<String> unparsList = new ArrayList<>();
		unparsList.add("Monday, March 5 06:00 AM - 01:00 PM");
		unparsList.add("Tuesday, March 6 06:00 AM - 10:00 AM");
		unparsList.add("Wednesday, March 7 06:00 AM - 10:00 AM");
		unparsList.add("Thursday, March 8 Not Scheduled");
		unparsList.add("Friday, March 9 Not Scheduled");
		unparsList.add("Saturday, March 10 Not Scheduled");
		unparsList.add("Sunday, March 11 Not Scheduled");

		DateTime currentDate = new DateTime(System.currentTimeMillis());
		String strCurrentDate = currentDate.toString();
		// TODO don't think this does anything
		// for (int i = 0; i < schedule.length; i++) {
		//
		// unparsList.add(schedule[i] + " " + schedule[i+1]);
		//
		// i++;
		// }

		for (String s : unparsList) {
			String Day = "";
			String Month = "";
			String StartTime = "";
			String EndTime = "";
			String currChar;
			int spaceCount = 0;
			int position = 0;
			String SpareString = "";
			String SpareString2 = "";
			while (position < s.length()) {

				currChar = s.substring(position, position + 1);
				if (currChar.equals(" ")) {

					s = s.substring(position);
					spaceCount++;
					position = 0;
				} else if (spaceCount == 1) {

					Month = Month + s.substring(position, position + 1);

				} else if (spaceCount == 2) {
					Day = Day + s.substring(position, position + 1);

				} else if (spaceCount == 3) {
					StartTime = StartTime + s.substring(position, position + 1);

				} else if (spaceCount == 4) {
					SpareString = SpareString + s.substring(position, position + 1);
				} else if (spaceCount == 6) {
					EndTime = EndTime + s.substring(position, position + 1);
				} else if (spaceCount == 7) {

					SpareString2 = SpareString2 + s.substring(position, position + 1);

				}

				position++;
			}
			Month = monthCheck(Month);
			Day = String.format("%02d", Integer.parseInt(Day));

			if (SpareString.equals("PM")) {

				StartTime = to24Hour(StartTime);

			}
			if (SpareString2.equals("PM")) {

				EndTime = to24Hour(EndTime);

			}

			if (!StartTime.equals("Not")) {
				System.out.println("StartTime:");
				System.out.println(
						strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + StartTime + ":00" + "-08:00");
				dtList.add(new DateTime(
						strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + StartTime + ":00" + "-08:00"));
				System.out.println("StopTime:");
				System.out.println(
						strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime + ":00" + "-08:00");
				dtList.add(new DateTime(
						strCurrentDate.substring(0, 4) + "-" + Month + "-" + Day + "T" + EndTime + ":00" + "-08:00"));
			}
		}
		System.out.println(strCurrentDate);
		// for (String i : unparsList) {
		//
		// System.out.println(i);
		//
		// }
		System.out.println(strCurrentDate);
		
		return dtList;
	}

	/**
	 * END Debugging Method.
	 */
	
}
