import java.nio.file.Path;

public class AddCmd extends LibraryCommand {
	
	private Path parsedArgument;
	
	/**
	 * Create the specified command and initialise it with
	 * the given command argument.
	 * @param argumentInput argument input as expected by the extending subclass.
	 *
	 * @throws IllegalArgumentException if given arguments are invalid
	 * @throws NullPointerException if any of the given parameters are null.
	 */
	public AddCmd(String argumentInput) {
		super(CommandType.ADD, argumentInput);
	}
	
	@Override
	protected boolean parseArguments(String argumentInput) {
		if(argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		boolean result = true;
		result = argumentInput.endsWith(".csv");
		if(result) {
			parsedArgument = Path.of(argumentInput);
		}
		return result;
	}
	
	@Override
	public void execute(LibraryData data) {
		if(data == null) {
			throw new NullPointerException("no entry");
		}
		data.loadData(parsedArgument);
	}
	
}
