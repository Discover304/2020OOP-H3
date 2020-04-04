import java.nio.file.Path;
import java.util.Objects;

/**
 * this is the class extends library command, used for execute command ADD
 * add list of book to the library
 */
public class AddCmd extends LibraryCommand {

    /**
     * passing argument
     */
    private Path argumentInput;

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

    /**
     * Parses the given command arguments and initialised necessary parameters.
     * @param argumentInput argument input for this command
     *
     * @return the result to show if the operation is done
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput,"no entry");

        if (argumentInput.equals("")) {
            return false;
        }

        boolean result;
        result = argumentInput.endsWith(".csv");
        if (result) {
            this.argumentInput = Path.of(argumentInput);
        }
        return result;
    }

    /**
     * execute ADD command to add some book to the library
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data,"no entry");
        data.loadData(argumentInput);
    }

}
