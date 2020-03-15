public class SearchCmd extends LibraryCommand {
	
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
	
	@Override
	public void execute(LibraryData data) {
		if(data == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		boolean noResult = true;
		for(BookEntry i : data.getBookData()) {
			if(i.getTitle().toLowerCase().contains(argumentInput.toLowerCase())){
				System.out.println(i.getTitle());
				noResult=false;
			}
		}
		if(noResult){
			System.out.println("No hits found for search term: "+argumentInput);
		}
	}
	
}
