package com.flo.graph.test.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.flo.graph.test.model.User;

public class UserReader {

	 private final File fFile;
	 private ArrayList<String> OCCUPATIONS;
	  
		/**
		   Constructor.
		   @param aFileName full name of an existing, readable file.
		  */
		  public UserReader(String aFileName){
		    fFile = new File(aFileName);  
		    OCCUPATIONS= new ArrayList<String>();
		    OCCUPATIONS.add("other");
		    OCCUPATIONS.add("academic/educator");
		    OCCUPATIONS.add("artist");
		    OCCUPATIONS.add("clerical/admin");
		    OCCUPATIONS.add("college/grad student");
		    OCCUPATIONS.add("customer service");
		    OCCUPATIONS.add("doctor/health care");
		    OCCUPATIONS.add("executive/managerial");
		    OCCUPATIONS.add("farmer");
		    OCCUPATIONS.add("homemaker");
		    OCCUPATIONS.add("K-12 student");
		    OCCUPATIONS.add("lawyer");
		    OCCUPATIONS.add("programmer");
		    OCCUPATIONS.add("retired");
		    OCCUPATIONS.add("sales/marketing");
		    OCCUPATIONS.add("scientist");
		    OCCUPATIONS.add("self-employed");
		    OCCUPATIONS.add("technician/engineer");
		    OCCUPATIONS.add("tradesman/craftsam");
		    OCCUPATIONS.add("unemployed");
		    OCCUPATIONS.add("writer");
		    
		    }
		  
		  /** Template method that calls {@link #processLine(String)}.  */
		  public final List<User> processLineByLine() throws FileNotFoundException {
		    //Note that FileReader is used, not File, since File is not Closeable
		    Scanner scanner = new Scanner(new FileReader(fFile));
		    ArrayList<User> users= new ArrayList<User>();
		    try {
		      //first use a Scanner to get each line
		      while ( scanner.hasNextLine() ){
		        users.add(processLine( scanner.nextLine() ));
		      }
		    }
		    finally {
		      //ensure the underlying stream is always closed
		      //this only has any effect if the item passed to the Scanner
		      //constructor implements Closeable (which it does in this case).
		      scanner.close();
		      
		    }
		    return users;
		  }
		  
		  protected User processLine(String aLine){
		    //use a second Scanner to parse the content of each line 
		    Scanner scanner = new Scanner(aLine);
		    scanner.useDelimiter("::");
		    //1::F::1::10::48067
		  
		    if ( scanner.hasNext() ){
		      String rawUserId = scanner.next();
		      String gender = scanner.next();
		      String rawAge=scanner.next();
		      String rawOccupation=scanner.next();
		      String zipCode=scanner.next();
		      int occupationIndex= Integer.parseInt(rawOccupation);
		      return new User(Integer.valueOf(rawUserId),gender,Integer.valueOf(rawAge),OCCUPATIONS.get(occupationIndex), zipCode, processZipCode(zipCode));
		    }
		    else {
		      log("Empty or invalid line. Unable to process.");
		      return null;
		    }
		    //no need to call scanner.close(), since the source is a String
		  }
		  
		  private String processZipCode(String zipCode)
		  {
			//TODO Match zipCode to State
			  return "State";
		  }
		  
		  private static void log(Object aObject){
		    System.out.println(String.valueOf(aObject));
		  }


}
