/* this program is an extension of CreateBankFile.java
and allows user to search for a specific bankholder based
on the account number. */

import java.nio.file.*;
import java.io.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class ReadBankAccounts
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String userInput;

      //object to locate AccountRecords.txt file
		Path filename = Paths.get("AccountRecords.txt");
		Path file = filename.toAbsolutePath();

      //initialize variables
		final String QUIT = "QUIT";
		final String ACCOUNT_NUMBER_FORMAT = "0000";
		final String NAME_FORMAT = "        ";
		final int NAME_LENGTH = NAME_FORMAT.length();
		final String BALANCE_FORMAT = "00000.00";
		final String delimiter = ",";

      //initialize string for record formnat
		String defaultRecord = ACCOUNT_NUMBER_FORMAT + delimiter + NAME_FORMAT + delimiter + BALANCE_FORMAT + System.getProperty("line.separator");
		final int RECORD_SIZE = defaultRecord.length();
		String acctString;
		int acct;
		byte[] data = defaultRecord.getBytes();
		String s;
   
      //ask user for account number
		System.out.print("Enter the number of the account to view >> ");
		userInput = input.nextLine();

      
		try
		{
         //creates object to read AccountRecords.txt
			FileChannel fc = (FileChannel)Files.newByteChannel(file, READ);

         //loops until user inputs QUIT
			while(!(userInput.equals(QUIT)))
			{
				ByteBuffer buffer = ByteBuffer.wrap(data);
				acct = Integer.parseInt(userInput);

            //locates the account number
				fc.position(acct * RECORD_SIZE);
            
            //reads and assigns account number
				fc.read(buffer);
				s = new String(data);

				prettyPrint(s.split(delimiter));
            
            //asks user for account number
				System.out.print("Enter the number of the account to view or " + QUIT + " >> ");
				userInput = input.nextLine();
			}

			fc.close();
		}
		catch(Exception e)   //error message
		{
			System.out.println("Error message: " + e);
		}

		input.close();
	}

	public static void prettyPrint(String[] c)
	{
		StringBuilder sb = new StringBuilder();

      //assigns the line read to a string
		for(String s : c)
			sb.append(s + " ");

		System.out.println(sb.toString());
	}
}