import java.util.ArrayList;
import java.util.Scanner;

/**
 * this is the class extends library command, used for execute command remove
 * remove the book that entered
 */
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

    /**
     * Parses the given command arguments and initialised necessary parameters.
     * @param argumentInput argument input for this command
     *
     * @return the result to show if the operation is done
     */
    @Override
    protected boolean parseArguments(String argumentInput) {
        if (argumentInput == null) {
            throw new NullPointerException("no entry");
        }

        if (argumentInput.equals("")) {
            return false;
        }

        argumentInput = argumentInput.trim();

        String[] parsedInput = new String[2];
        if (!readStringContents(argumentInput, parsedInput)) {
            return false;
        }

        if (parsedInput[0].equals("AUTHOR") || parsedInput[0].equals("TITLE")) {
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
        if (!scanner.hasNext()) {
            return false;
        }
        parsedInput[0] = scanner.next();
        if (!scanner.hasNext()) {
            return false;
        }
        parsedInput[1] = scanner.nextLine();
        scanner.close();
        parsedInput[1] = parsedInput[1].substring(1);
        return true;
    }

    /**
     * execute remove command and print the result to console
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        if (data == null || argumentInput == null) {
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
        for (BookEntry book :  new ArrayList<>(data.getBookData())) {
            if (isTheTitle(book)) {
                data.getBookData().remove(book);
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
        for (BookEntry book : new ArrayList<>(data.getBookData())) {
            if (isTheAuthor(book)) {
                data.getBookData().remove(book);
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
        if (theBook == null || argumentInput == null) {
            throw new NullPointerException("no entry");
        }

        for (String author : theBook.getAuthors()) {
            if (argumentInput[1].equals(author)) {
                return true;
            }
        }
        return false;
    }

}
