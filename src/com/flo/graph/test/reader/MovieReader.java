package com.flo.graph.test.reader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.flo.graph.test.model.Movie;



public class MovieReader {

	

	  private final File fFile;
	  
	/**
	   Constructor.
	   @param aFileName full name of an existing, readable file.
	  */
	  public MovieReader(String aFileName){
	    fFile = new File(aFileName);  
	  }
	  
	  /** Template method that calls {@link #processLine(String)}.  */
	  public final List<Movie> processLineByLine() throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
	    Scanner scanner = new Scanner(new FileReader(fFile));
	    ArrayList<Movie> movies= new ArrayList<Movie>();
	    try {
	      //first use a Scanner to get each line
	      while ( scanner.hasNextLine() ){
	        movies.add(processLine( scanner.nextLine() ));
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	      
	    }
	    return movies;
	  }
	  
	  protected Movie processLine(String aLine){
	    //use a second Scanner to parse the content of each line 
	    Scanner scanner = new Scanner(aLine);
	    scanner.useDelimiter("::");
	    //5::Father of the Bride Part II (1995)::Comedy

	    if ( scanner.hasNext() ){
	      String movieId = scanner.next();
	      String title = scanner.next();
	      String generas=scanner.next();
	      return new Movie(Integer.valueOf(movieId),title,processGeneras(generas));
	    }
	    else {
	      log("Empty or invalid line. Unable to process.");
	      return null;
	    }
	    //no need to call scanner.close(), since the source is a String
	  }
	  
	  private List<String> processGeneras(String generas)
	  {
		  Scanner scanner= new Scanner(generas);
	      scanner.useDelimiter("\\|");
          ArrayList<String> generasList = new ArrayList<String>();
	      while(scanner.hasNext())
          {
        	  generasList.add(scanner.next());
          }
	      return generasList;
	  }
	  
	  private static void log(Object aObject){
	    System.out.println(String.valueOf(aObject));
	  }
	  
	} 

