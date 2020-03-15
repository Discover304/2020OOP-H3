import java.util.ArrayList;
import java.util.Scanner;

public class RemoveCmd extends LibraryCommand {
	
	/**
	 * used to pass argument
	 */
	private String[] argumentInput;
	
	/**
	 * Create the specified command and initialise it with
	 * the given command argument.
	 * @param argumentInput argument input as expected by the extending subclass.
	 *
	 * @throws IllegalArgumentException if given arguments are invalid
	 * @throws NullPointerException if any of the given parameters are null.
	 */
	public RemoveCmd(String argumentInput) {
		super(CommandType.REMOVE, argumentInput);
	}
	
	@Override
	protected boolean parseArguments(String argumentInput) {
		if(argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		if(argumentInput.equals("")) {
			return false;
		}
		
		argumentInput = argumentInput.trim();
		
		String[] parsedInput = new String[2];
		if(! readStringContents(argumentInput, parsedInput)) {
			return false;
		}
		
		if(parsedInput[0].equals("AUTHOR") || parsedInput[0].equals("TITLE")) {
			this.argumentInput = parsedInput;
			return true;
		}
		return false;
	}
	
	/**
	 * read the content of a String and return false if error occur
	 * @param argumentInput the input
	 * @param parsedInput the variable to store value
	 *
	 * @return boolean value to show if the function work correctly
	 */
	private boolean readStringContents(String argumentInput, String[] parsedInput) {
		Scanner scanner = new Scanner(argumentInput);
		if(! scanner.hasNext()) {
			return false;
		}
		parsedInput[0] = scanner.next();
		if(! scanner.hasNext()) {
			return false;
		}
		parsedInput[1] = scanner.nextLine();
		scanner.close();
		parsedInput[1] = parsedInput[1].substring(1);
		return true;
	}
	
	@Override
	public void execute(LibraryData data) {
		if(data == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		switch (argumentInput[0]) {
			case "TITLE": {
				removeTitle(data);
				break;
			}
			case "AUTHOR": {
				removeAuthor(data);
			}
		}
	}
	
	/**
	 * remove the data has the same title
	 * @param data the input data
	 */
	private void removeTitle(LibraryData data) {
		for(BookEntry i : new ArrayList<>(data.getBookData())) {
			if(isTheTitle(i)) {
				data.getBookData().remove(i);
				System.out.println(argumentInput[1] + ": removed successfully.");
				return;//assume only one book with the same name
			}
		}
		System.out.println(argumentInput[1] + ": not found.");
	}
	
	/**
	 * see if the book is the one we want
	 * @param theBook the book entry, waiting for judge
	 *
	 * @return a boolean to show the result
	 */
	private boolean isTheTitle(BookEntry theBook) {
		return theBook.getTitle().equals(argumentInput[1]);
	}
	
	/**
	 * remove the book with the author
	 * @param data the input data
	 */
	private void removeAuthor(LibraryData data) {
		int counter = 0;
		for(BookEntry i : new ArrayList<>(data.getBookData())) {
			if(isTheAuthor(i)) {
				data.getBookData().remove(i);
				counter += 1;
			}
		}
		System.out.println(counter + " books removed for author: " + argumentInput[1]);
	}
	
	/**
	 * this is to decide if the book has the author
	 * @param theBook the book entry
	 *
	 * @return bool the result
	 */
	private boolean isTheAuthor(BookEntry theBook) {
		if(theBook == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		for(String i : theBook.getAuthors()) {
			if(argumentInput[1].equals(i)) {
				return true;
			}
		}
		return false;
	}
	
}
