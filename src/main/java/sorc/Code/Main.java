package sorc.Code;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.spec.SecretKeySpec;

import com.google.api.client.util.DateTime;

public class Main {
	// Instantiating objects for use in project.
	private static ToCal cal = new ToCal();
	private GetScedual s = new GetScedual();
	private EncyptPlz enP = new EncyptPlz();
	private ConvertTime convert = new ConvertTime();
	

	// Vars for userdata (Really only used on first run. Maybe Move to new class?)
	private String pNums = "";
	private String pPass = "";
	private String[] pQues = { "", "", "" };
	private String[] pAwns = new String[3];

	public static void main(String[] args) throws IOException {
		Main m = new Main();
		m.run();

		/**
		 * Debugging
		 */
		// cal.listitems();
		// ArrayList<DateTime> D = new ArrayList<>();
		// cal.toCal(D);
		// m.convertTimeTest();
		/**
		 * END Debugging
		 */

	}

	/**
	 * Run starts the meat of the program.
	 */
	private void run() {

		ReadUserData reader = new ReadUserData();
		Settings settings = new Settings();
		
		settings.createSettings();

		if (!reader.fileExists()) {

			try {
				noFileRecord();
			} catch (GeneralSecurityException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (reader.fileExists()) {
			try {
				fileRecord();
			} catch (GeneralSecurityException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			System.out.println("No file found!");

		}
	}

	/**
	 * noFileRecord()
	 * 
	 * If no file is found then create a new user credentials file.
	 * 
	 * @param io
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private void noFileRecord() throws GeneralSecurityException, IOException {
		WriteUserData writer = new WriteUserData();
		byte[] salt = new String("12345678").getBytes();
		int iterationCount = 40000;
		int keyLength = 128;

		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

		// String Master = "";

		System.out.println("Please Enter A Master Password (You will only need to use this next time): ");
		SecretKeySpec key = enP.createSecretKey(in.nextLine().toCharArray(), salt, iterationCount, keyLength);
		System.out.println("Please Enter Your Partner Numbers( USXXXXXXX ): ");
		pNums = enP.encrypt(in.nextLine(), key);
		System.out.println("Please Enter Your Partner Hub Password: ");
		pPass = enP.encrypt(in.nextLine(), key);
		System.out.println("Next you'll need to enter your security questions."
				+ " \nThis program can prob for the questions of you can enter them yourself. "
				+ " \nWould you like the software to auto-attempt to find them? (Y/n)");
		if (in.nextLine().toLowerCase().equals("y")) {
			prob(key);
		} else {
			System.out.println("Please Enter Your Security Question: ");
			pQues[0] = enP.encrypt(in.nextLine(), key);
			System.out.println("Question Answer: ");
			pAwns[0] = enP.encrypt(in.nextLine(), key);
			System.out.println("Please Enter Your Security Question: ");
			pQues[1] = enP.encrypt(in.nextLine(), key);
			System.out.println("Question Answer: ");
			pAwns[1] = enP.encrypt(in.nextLine(), key);
			System.out.println("Please Enter Your Security Question: ");
			pQues[2] = enP.encrypt(in.nextLine(), key);
			System.out.println("Question Answer: ");
			pAwns[2] = enP.encrypt(in.nextLine(), key);
		}

		ArrayList<String> data = new ArrayList<>();
		data.add(pNums);
		data.add(pPass);
		data.add(pQues[0]);
		data.add(pAwns[0]);
		data.add(pQues[1]);
		data.add(pAwns[1]);
		data.add(pQues[2]);
		data.add(pAwns[2]);

		writer.writeFile(data);

	}

	/**
	 * If a file is found then use it and run the program.
	 * 
	 * @param io
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	// File Does exist (Retrun User)
	private void fileRecord() throws GeneralSecurityException, IOException {
		ReadUserData reader = new ReadUserData();
		GetScedual s = new GetScedual(); // Instance of GetScedual.
		String[] Schedule = new String[28]; // Holds Schedule info from the partner hub.
		ArrayList<String> data = new ArrayList<>(); // Holds User data from file.
		ArrayList<DateTime> dt; // Holds converted time from Schedule (Could probably be an array not
								// arraylist.)
		EncyptPlz enP = new EncyptPlz(); // Instance of EncyptPlz. (YES I KNOW ITS SPELLED WRONG. I'm too lazy to fix
											// it.)
		@SuppressWarnings("resource") // TODO Need to know what this issue is.
		Scanner in = new Scanner(System.in); // Used to get MasterPass
		// Reads Credentials File
		data = reader.readFile();

		// Encryption things TODO: Look into this more
		byte[] salt = new String("12345678").getBytes();
		int iterationCount = 40000;
		int keyLength = 128;

		System.out.println("Please Enter A Master Password: ");
		SecretKeySpec key = enP.createSecretKey(in.nextLine().toCharArray(), salt, iterationCount, keyLength);
		// Importing data into array to be used when getting the Schedule from the HUB.
		String[] Q = new String[3];
		String[] A = new String[3];
		Q[0] = enP.decrypt(data.get(2), key);
		Q[1] = enP.decrypt(data.get(4), key);
		Q[2] = enP.decrypt(data.get(6), key);// TODO: find a way to get the third security question.
		A[0] = enP.decrypt(data.get(3), key);
		A[1] = enP.decrypt(data.get(5), key);
		A[2] = enP.decrypt(data.get(7), key);// TODO: find a way to get the third security question.

		// This call is what actually gets the schedule.
		Schedule = s.getScedual(enP.decrypt(data.get(0), key), enP.decrypt(data.get(1), key), Q, A);
		dt = convert.convertTime(Schedule);

		/**
		 * Debug
		 */
		for (DateTime t : dt) {

			System.out.println(t);

		}
		/**
		 * END Debug
		 */
		// Imports to google calendar. TODO: This shouldn't be here should be in run.
		cal.toCal(dt);
	}

	/**
	 * Prob this method auto finds security questions for you so you don't have to
	 * manually enter them. TODO: get 3rd question.
	 * 
	 * @param key
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */

	private void prob(SecretKeySpec key) throws GeneralSecurityException, IOException {
		Scanner in = new Scanner(System.in);
		int count = 10; // Number of times it will load the page to find question.
		boolean done = false; // Tracks if the array has been through count times.
		// Was going to use a for loop but don't remember why I chose not to.
		while (count > 0 && !done) {
			String returnString = s.prob(enP.decrypt(pNums, key));

			// This is to make sure two of the same questions don't get inserted into the
			// array.
			if (pQues[0].equals("")) {
				pQues[0] = returnString;

			} else if (pQues[1].equals("")) {
				if (!pQues[0].equals(returnString)) {
					pQues[1] = returnString;
					// This is hard codded and if a partner were to get a third security question
					// this code wouldnt get it.
					// TODO: Fix this!!
					done = true;
					System.out.println("Done");
				}
			} else if (pQues[2].equals("")) {
				if (!pQues[0].equals(returnString) && !pQues[1].equals(returnString)) {
					pQues[2] = returnString;
				}
			}
			count--;
		}
		// gets answers from user and Encrypts Questions and Answers.
		for (int i = 0; i < pQues.length; i++) {
			if (!pQues[i].equals("")) {
				System.out.println(pQues[i]);
				pQues[i] = enP.encrypt(pQues[i], key);
				pAwns[i] = enP.encrypt(in.nextLine(), key);
			}
		}
		// Again more hard code.
		// TODO: Fix this too. See above
		if (pQues[2].equals("")) {
			// badPractice
			pQues[2] = enP.encrypt("noValue", key);
			pAwns[2] = enP.encrypt("noValue", key);

		}
	
		in.close();

	}
	




}
