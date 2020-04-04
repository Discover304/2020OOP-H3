import java.util.ArrayList;
import java.util.Objects;

/**
 * this is the class extends library command, used for execute command remove
 * remove the book that entered
 */
public class RemoveCmd extends LibraryCommand {

    /**
     * used to pass argument
     */
    private String option;

    /**
     * the entry waiting for remove
     */
    private String removeValue;

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

    /**
     * Parses the given command arguments and initialised necessary parameters.
     * @param argumentInput argument input for this command
     *
     * @return the result to show if the operation is done
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        Objects.requireNonNull(argumentInput, "no entry");

        if (argumentInput.equals("")) {
            return false;
        }

        String separator = " ";
        argumentInput = argumentInput.trim();
        if (argumentInput.contains(separator)) {
            int separateIndex = argumentInput.indexOf(separator);

            option = argumentInput.substring(0, separateIndex);
            removeValue = argumentInput.substring(separateIndex + 1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * execute remove command and print the result to console
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        Objects.requireNonNull(data, "no entry");
        Objects.requireNonNull(option, "no entry");
        Objects.requireNonNull(removeValue, "no entry");

        switch (option) {
            case TITLE: {
                removeTitle(data);
                break;
            }
            case AUTHOR: {
                removeAuthor(data);
            }
        }
    }

    /**
     * remove the data has the same title
     * @param data the input data
     */
    private void removeTitle(LibraryData data) {
        for (BookEntry book : new ArrayList<>(data.getBookData())) {
            if (isTheTitle(book)) {
                data.getBookData().remove(book);
                System.out.println(removeValue + ": removed successfully.");
                return;//assume only one book with the same name
            }
        }
        System.out.println(removeValue + ": not found.");
    }

    /**
     * see if the book is the one we want
     * @param theBook the book entry, waiting for judge
     *
     * @return a boolean to show the result
     */
    private boolean isTheTitle(BookEntry theBook) {
        return theBook.getTitle().equals(removeValue);
    }

    /**
     * remove the book with the author
     * @param data the input data
     */
    private void removeAuthor(LibraryData data) {
        int counter = 0;
        for (BookEntry book : new ArrayList<>(data.getBookData())) {
            if (isTheAuthor(book)) {
                data.getBookData().remove(book);
                counter += 1;
            }
        }
        System.out.println(counter + " books removed for author: " + removeValue);
    }

    /**
     * this is to decide if the book has the author
     * @param theBook the book entry
     *
     * @return bool the result
     */
    private boolean isTheAuthor(BookEntry theBook) {
        for (String author : theBook.getAuthors()) {
            if (removeValue.equals(author)) {
                return true;
            }
        }
        return false;
    }

}
