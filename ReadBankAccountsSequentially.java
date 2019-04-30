/* This program reads all bank holder info. written in the
.txt file in order. */

import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;

public class ReadBankAccountsSequentially
{
	public static void main(String[] args)
	{
      //object to locate AccountRecords.txt
		Path filename = Paths.get("AccountRecords.txt");
		Path file = filename.toAbsolutePath();

		final String delimiter = ",";

		try
		{
         //create object to read file
			InputStream inputFile = new BufferedInputStream(Files.newInputStream(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));

         //initialize the line read to a string
			String s = reader.readLine();

			while(s != null)  //loops until no more lines are read
			{
				String[] array = s.split(delimiter);
            
            //loop prints out account line for all account holders
				if(!((array[1].trim()).isEmpty()))
					System.out.println("Account Number: " + array[0] + "\nAccount Holder: " + array[1]+ "\nBalance: $" + array[2] + "\n-------");

				s = reader.readLine();
			}

			reader.close();
		}
		catch(Exception e)   //error message
		{
			System.out.println("Error message: " + e);
		}
	}
}