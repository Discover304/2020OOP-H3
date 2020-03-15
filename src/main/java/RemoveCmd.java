import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoveCmd extends LibraryCommand {
	
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
		
		//scan for the header and the data
		String[] parsedInput = new String[2];
		Scanner scanner = new Scanner(argumentInput);
		parsedInput[0] = scanner.next();
		if(! scanner.hasNext()) {
			return false;
		}
		parsedInput[1] = scanner.nextLine();
		scanner.close();
		parsedInput[1] = parsedInput[1].substring(1);
		
		//pass value
		if(parsedInput[0].equals("AUTHOR") || parsedInput[0].equals("TITLE")) {
			this.argumentInput = parsedInput;
			return true;
		}
		return false;
	}
	
	@Override
	public void execute(LibraryData data) {
		if(data == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		switch (argumentInput[0]) {
			case "TITLE": {
				removeTitle(data);
			}
			case "AUTHORS": {
				removeAuthor(data);
			}
		}
	}
	
	private void removeTitle(LibraryData data) {
		boolean result = false;
		int removeIndex = 0;
		for(int i = 0; i < data.getBookData().size(); i++) {
			if(isTheTitle(data.getBookData().get(i))) {
				result      = true;
				removeIndex = i;
				break;//assume only one book with the same name
			}
		}
		if(result) {
			data.getBookData().remove(removeIndex);
			System.out.println(argumentInput[1] + ": removed successfully.");
		} else {
			System.out.println(argumentInput[1] + ": not found.");
		}
		
	}
	
	private boolean isTheTitle(BookEntry theBook) {
		return theBook.getTitle().equals(argumentInput[1]);
	}
	
	private void removeAuthor(LibraryData data) {
		int counter = 0;
		ArrayList<BookEntry> removedBook = new ArrayList<BookEntry>();
		
		for(int i = 0; i < data.getBookData().size(); i++) {
			if(isTheAuthor(data.getBookData().get(i))) {
				counter = counter + 1;
				data.getBookData().remove(data.getBookData().get(i));
			}
		}
		System.out.println(counter + " removed for author: " + argumentInput[1]);
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
