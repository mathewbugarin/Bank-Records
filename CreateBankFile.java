/* this program serves an example of creating a file through java.
The program creates a .txt in the same file location, and writes
bank holder information in each line. Program is run on while loop
until user suggests so.*/

import java.nio.file.*;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import static java.nio.file.StandardOpenOption.*;
import java.util.Scanner;
import java.text.*;

public class CreateBankFile
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
      
      //object to create AccountRecords.txt file
		Path filename = Paths.get("AccountRecords.txt");
		Path file = filename.toAbsolutePath();

      //sets 1000 blank records
		final int NUMBER_OF_RECORDS = 10000; 
      
      //initialize account num/name/balance limits
		final String ACCOUNT_NUMBER_FORMAT = "0000";
		final String NAME_FORMAT = "        ";
		final int NAME_LENGTH = NAME_FORMAT.length();
		final String BALANCE_FORMAT = "00000.00";
		final String delimiter = ",";
      
      //sets string for the record format
		String defaultRecord = ACCOUNT_NUMBER_FORMAT + delimiter + NAME_FORMAT + delimiter + BALANCE_FORMAT + System.getProperty("line.separator");
		final int RECORD_SIZE = defaultRecord.length();

      //initialize variables
		FileChannel fc = null;
		String acctString;
		int acct;
		String name;
		double balance;
		byte[] data;
		ByteBuffer buffer;
		final String QUIT = "QUIT";

      
      //method call creates empty file based on user input variables
		createEmptyFile(file, defaultRecord, NUMBER_OF_RECORDS);

		try
		{
			fc = (FileChannel)Files.newByteChannel(file, CREATE, WRITE);

         //asks user to input account number
			System.out.print("Enter 4-Digit Account Number or " + QUIT + " >> ");
			acctString = input.nextLine();
 
         //if user inputs account number, asks for name/balance and etc
         //loops until user enters 'QUIT'
			while(!(acctString.equals(QUIT)))
			{
				acct = Integer.parseInt(acctString);

            //asks user to input last name
				System.out.print("Account Holder last name: " + acctString + " >> ");
				name = input.nextLine();
				StringBuilder sb = new StringBuilder(name);
				sb.setLength(NAME_LENGTH);
				name = sb.toString();
            
            //asks user to input balance
				System.out.print("Balance of Account: " + acctString + " >> ");
				balance = input.nextDouble();
				input.nextLine();
				DecimalFormat df = new DecimalFormat(BALANCE_FORMAT);

            //string to format account number/name/balance to be written
				String s = acctString + delimiter + name + delimiter + df.format(balance) + System.getProperty("line.separator");
				data = s.getBytes();
				buffer = ByteBuffer.wrap(data);

				fc.position(acct * RECORD_SIZE);
				fc.write(buffer);

				System.out.print("\nEnter 4-Digit Account Number or " + QUIT + " >> ");
				acctString = input.nextLine();
			}

			fc.close();
		}
		catch(Exception e) //error message if user inputs wrong data
		{
			System.out.println("Error message: " + e);
		}

		input.close();
	}

   //method to create empty file
	public static void createEmptyFile(Path file, String s, int lines)
	{
		try
		{
			OutputStream outputStr = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStr));

			for(int count = 0; count < lines; ++count)
				writer.write(s, 0, s.length());

			writer.close();
		}
		catch(Exception e)
		{
			System.out.println("Error message: " + e);
		}
	}
}