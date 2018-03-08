package sorc.Code;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToCal {
	/** Application name. */
	private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/calendar-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/calendar-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = ToCal.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * 
	 * @return an authorized Calendar client service
	 * @throws IOException
	 */
	public com.google.api.services.calendar.Calendar getCalendarService() throws IOException {
		Credential credential = authorize();
		return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(APPLICATION_NAME).build();
	}

	public void addevent(com.google.api.services.calendar.Calendar service, String Summery, DateTime sTime,
			DateTime eTime, String Id) throws IOException {
		boolean eventExists = false; // this system is broken
		EventDateTime t;
		String pageToken = null;

//		com.google.api.services.calendar.model.Calendar calendar = service.calendars().get(Id).execute();

		do {
			Events events = service.events().list(Id).setPageToken(pageToken).execute();
			List<Event> items = events.getItems();
			eventExists = false;
			for (Event event : items) {
				t = event.getStart();
				if (sTime.equals(t.getDateTime())) {
					eventExists = true;
					// System.out.println("set true");
				}
				/*** DEBUGGING */
				// System.out.println(event.getSummary());
				System.out.println(event.getSummary());
				System.out.print("sTime: ");
				System.out.println(sTime);
				System.out.print("t.getDateTime(): ");
				System.out.println(t.getDateTime());

				/*** END DEBUGGING */
			}
			pageToken = events.getNextPageToken();
		} while (pageToken != null);
		if (!eventExists) {
			Event eventadd = new Event().setSummary(Summery);

			DateTime startDateTime = sTime;
			EventDateTime startT = new EventDateTime().setDateTime(startDateTime);
			System.out.print("startT: ");
			System.out.println(startT);
			eventadd.setStart(startT);

			DateTime endDateTime = eTime;// new DateTime("2018-03-05T17:00:00-07:00");
			EventDateTime endT = new EventDateTime().setDateTime(endDateTime);
			System.out.print("endT: ");
			System.out.println(endT);
			eventadd.setEnd(endT);

			eventadd = service.events().insert(Id, eventadd).execute();
			System.out.printf("Event created: %s\n", eventadd.getHtmlLink());
		}

	}

	/**
	 * Writes to google Cal
	 * @param D
	 * @throws IOException
	 */
	public void toCal(ArrayList<DateTime> D) throws IOException {
		// Build a new authorized API client service.
		// Note: Do not confuse this class with the
		// com.google.api.services.calendar.model.Calendar class.
		com.google.api.services.calendar.Calendar service = getCalendarService();

		// List the next 10 events from the primary calendar.
//		DateTime now = new DateTime(System.currentTimeMillis());
		boolean found = false;
		String pageToken = null;
		String calId = "";

		// com.google.api.services.calendar.model.Events events =
		// service.events().list("primary").setMaxResults(2)
		// .setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();

		do {
			com.google.api.services.calendar.model.CalendarList calendarList = service.calendarList().list()
					.setPageToken(pageToken).execute();
			List<CalendarListEntry> items = calendarList.getItems();

			for (CalendarListEntry calendarListEntry : items) {
				if (calendarListEntry.getSummary().toLowerCase().equals("work")) {

					calId = calendarListEntry.getId();
					found = true;

				}
				/**
				 * DEBUGGING
				 */
				// System.out.println(calendarListEntry.getSummary());
				// System.out.println(calendarListEntry.getId());
				/**
				 * END DEBUGGING
				 */
			}
			pageToken = calendarList.getNextPageToken();
		} while (pageToken != null);
		if (!found) {

			com.google.api.services.calendar.model.Calendar calendar = new Calendar();
			calendar.setSummary("Work");
			calendar.setTimeZone("America/Los_Angeles");

			Calendar createdCalendar = service.calendars().insert(calendar).execute();

			calId = createdCalendar.getId();
		}

		/**
		 * Adds elements to work cal;
		 */
		int i = 0;
		while (i < D.size()) {
			DateTime sTime = D.get(i);

			DateTime eTime = D.get(i + 1);

			addevent(service, "Work", sTime, eTime, calId);

			i = i + 2;
		}

		/**
		 * DEBUG
		 */
		// List<Event> items = events.getItems();
		// if (items.size() == 0) {
		// System.out.println("No upcoming events found.");
		// } else {
		// System.out.println("Upcoming events");
		// for (Event event : items) {
		// DateTime start = event.getStart().getDateTime();
		// if (start == null) {
		// start = event.getStart().getDate();
		// }
		// System.out.printf("%s (%s)\n", event.getSummary(), start);
		// }
		// }
		/**
		 * END DEBUG
		 */

	}
	
	
	/**
	 * DEBUG
	 */
	@SuppressWarnings("unused")
	public void listitems() throws IOException {
		com.google.api.services.calendar.Calendar service = getCalendarService();
		DateTime sdt = new DateTime("2018-03-10T06:00:00.000-08:00");
		DateTime edt = new DateTime("2018-03-10T09:00:00.000-08:00");
		boolean eventExists = false; // this system is broken
		EventDateTime t;
		String pageToken = null;
		addevent(service, "Work", sdt, edt, "tlip0v2s9ss6j8lgk1ki90v7t0@group.calendar.google.com");
		com.google.api.services.calendar.model.Calendar calendar = service.calendars()
				.get("tlip0v2s9ss6j8lgk1ki90v7t0@group.calendar.google.com").execute();

		do {
			Events events = service.events().list("tlip0v2s9ss6j8lgk1ki90v7t0@group.calendar.google.com")
					.setPageToken(pageToken).execute();
			List<Event> items = events.getItems();
			eventExists = false;
			for (Event event : items) {
				t = event.getStart();

				System.out.println(event.getStart());
			}
			pageToken = events.getNextPageToken();
		} while (pageToken != null);

	}
	/**
	 * END DEBUG
	 */
}