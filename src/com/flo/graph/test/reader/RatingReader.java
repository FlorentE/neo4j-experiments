package com.flo.graph.test.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.flo.graph.test.model.Rating;
import com.flo.graph.test.model.User;

public class RatingReader {

	 private final File fFile;
	 private ArrayList<String> OCCUPATIONS;
	  
		/**
		   Constructor.
		   @param aFileName full name of an existing, readable file.
		  */
		  public RatingReader(String aFileName){
		    fFile = new File(aFileName);  
		    
		    }
		  
		  /** Template method that calls {@link #processLine(String)}.  */
		  public final List<Rating> processLineByLine() throws FileNotFoundException {
		    //Note that FileReader is used, not File, since File is not Closeable
		    Scanner scanner = new Scanner(new FileReader(fFile));
		    ArrayList<Rating> users= new ArrayList<Rating>();
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
		  
		  protected Rating processLine(String aLine){
		    //use a second Scanner to parse the content of each line 
		    Scanner scanner = new Scanner(aLine);
		    scanner.useDelimiter("::");
		    //UserID::MovieID::Rating::Timestamp
		    if ( scanner.hasNext() ){
		      String rawUserId = scanner.next();
		      String rawMovieId = scanner.next();
		      String rawStars=scanner.next();
		      String rawTimestamp=scanner.next();
		      
		      Date ratingDate =new Date(Long.valueOf(rawTimestamp));
		      return new Rating(Integer.valueOf(rawUserId),Integer.valueOf(rawMovieId),Integer.valueOf(rawStars), ratingDate);
		    }
		    else {
		      log("Empty or invalid line. Unable to process.");
		      return null;
		    }
		    //no need to call scanner.close(), since the source is a String
		  }
		  
		  private static void log(Object aObject){
		    System.out.println(String.valueOf(aObject));
		  }


}
