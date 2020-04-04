import java.util.Objects;

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
     * long option
     */
    private final String LONG_OPTION = "long";

    /**
     * short option
     */
    private final String SHORT_OPTION = "short";

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
        Objects.requireNonNull(argumentInput, "no entry");

        argumentInput = argumentInput.trim();
        if (argumentInput.equals(LONG_OPTION) || argumentInput.equals(SHORT_OPTION) || argumentInput.equals("")) {
            this.argumentInput = argumentInput;
            return true;
        }
        return false;
    }

    /**
     * execute list command and print the result to console
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(argumentInput, "no entry");
        Objects.requireNonNull(data, "no entry");

        int sizeOfLibrary = data.getBookData().size();
        if(sizeOfLibrary ==0) {
            System.out.println("The library has no book entries.");
            return;
        }
        else {
            System.out.println(sizeOfLibrary + " books in library:");
        }

        switch (argumentInput){
            case "" :
            case SHORT_OPTION:{
                for (BookEntry i : data.getBookData()) {
                    System.out.println(i.getTitle());
                }
                break;
            }
            case LONG_OPTION :{
                for (BookEntry i : data.getBookData()) {
                    System.out.println(i.toString());
                    System.out.print("\n");
                }
            }
        }
    }
}
