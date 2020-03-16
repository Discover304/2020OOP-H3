/**
 * this is the class extends library command, used for execute command List
 * displaying a list of the content of the library
 */
public class ListCmd extends LibraryCommand {

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
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    /**
     * Parses the given command arguments and initialised necessary parameters.
     * @param argumentInput argument input for this command
     *
     * @return the result to show if the operation is done
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput == null) {
            return false;
        }

        argumentInput = argumentInput.trim();
        boolean result = argumentInput.equals("long") || argumentInput.equals("short") || argumentInput.equals("");
        if (result) {
            this.argumentInput = argumentInput.trim();
        }
        return result;
    }

    /**
     * execute list command and print the result to console
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        if (data == null || argumentInput == null) {
            throw new NullPointerException("no entry");
        }
        System.out.println(data.getBookData().size() + " books in library:");

        if (argumentInput.equals("") || argumentInput.equals("short")) {
            for (BookEntry i : data.getBookData()) {
                System.out.println(i.getTitle());
            }
        }

        if (argumentInput.equals("long")) {
            for (BookEntry i : data.getBookData()) {
                System.out.println(i.toString());
                System.out.print("\n");
            }
        }

    }

}
