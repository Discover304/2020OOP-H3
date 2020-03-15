public class ListCmd extends LibraryCommand {
	
	private String argumentInput;
	
	/**
	 * Create the specified command and initialise it with
	 * the given command argument.
	 * @param argumentInput argument input as expected by the extending subclass.
	 *
	 * @throws IllegalArgumentException if given arguments are invalid
	 * @throws NullPointerException if any of the given parameters are null.
	 */
	public ListCmd(String argumentInput) {
		super(CommandType.LIST, argumentInput);
	}
	
	@Override
	protected boolean parseArguments(String argumentInput) {
		if(argumentInput == null) {
			return false;
		}
		
		argumentInput = argumentInput.trim();
		boolean result = argumentInput.equals("long") || argumentInput.equals("short") || argumentInput.equals("");
		if(result) {
			this.argumentInput = argumentInput.trim();
		}
		return result;
	}
	
	@Override
	public void execute(LibraryData data) {
		if(data == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		System.out.println(data.getBookData().size() + " books in library:");
		
		if(argumentInput.equals("") || argumentInput.equals("short")) {
			for(BookEntry i : data.getBookData()) {
				System.out.println(i.getTitle());
			}
		}
		
		if(argumentInput.equals("long")) {
			for(BookEntry i : data.getBookData()) {
				System.out.println(i.toString());
				System.out.print("\n");
			}
		}
		
	}
	
}
