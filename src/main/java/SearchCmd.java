/**
 * this is the class extends library command, used for execute command search
 * find the required book in the library
 */
public class SearchCmd extends LibraryCommand {

	/**
	 * passing argument
	 */
	private String argumentInput;
	
	/**
	 * Create the specified command and initialise it with
	 * the given command argument.
	 * @param argumentInput argument input as expected by the extending subclass.
	 *
	 * @throws IllegalArgumentException if given arguments are invalid
	 * @throws NullPointerException if any of the given parameters are null.
	 */
	public SearchCmd(String argumentInput) {
		super(CommandType.SEARCH, argumentInput);
	}

	/**
	 * Parses the given command arguments and initialised necessary parameters.
	 * @param argumentInput argument input for this command
	 *
	 * @return the result to show if the operation is done
	 */
	@Override
	protected boolean parseArguments(String argumentInput) {
		if(argumentInput == null) {
			return false;
		}
		
		if(argumentInput.contains(" ") || argumentInput.equals("")) {
			return false;
		}
		
		this.argumentInput = argumentInput;
		return true;
	}

	/**
	 * execute search command and print the result to console
	 * @param data book data to be considered for command execution.
	 */
	@Override
	public void execute(LibraryData data) {
		if(data == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		boolean noResult = true;
		for(BookEntry book : data.getBookData()) {
			if(book.getTitle().toLowerCase().contains(argumentInput.toLowerCase())) {
				System.out.println(book.getTitle());
				noResult = false;
			}
		}
		if(noResult) {
			System.out.println("No hits found for search term: " + argumentInput);
		}
	}
	
}
