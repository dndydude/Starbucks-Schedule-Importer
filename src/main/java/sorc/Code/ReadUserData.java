package sorc.Code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadUserData {

	public ArrayList<String> readFile() throws FileNotFoundException {
		ArrayList<String> l = new ArrayList<>();
		String s = "";
		
		Scanner in = new Scanner(new File("Data.txt"));

		try {
			while (in.hasNext()) {
				s = in.nextLine();
				l.add(s);
			}
		} finally {
			in.close();
			System.out.println("Read successful from Data.txt");
		}
		return l;
	}
	
	public boolean fileExists() {
		File f = new File("Data.txt");
		if (f.exists()) {

			return true;

		} else {
			return false;
		}

	}
	
}
