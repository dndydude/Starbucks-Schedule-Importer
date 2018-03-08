package sorc.Code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteUserData {

	public void writeFile(ArrayList<String> s) throws FileNotFoundException {

		PrintWriter out = new PrintWriter(new File("Data.txt"));
		try {
			for (String i : s) {

				out.println(i);
			}

		} finally {
			out.close();
			// Notifies the user when the write is complete.
			System.out.println("Write complete. Wrote to file 'Data.txt'");
		}

	}
	
}
