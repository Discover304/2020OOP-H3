import java.util.*;

/**
 * this is the class extends library command, used for execute command GROUP
 */
public class GroupCmd extends LibraryCommand {

    /**
     * passing argument
     */
    private String argumentInput;

    /**
     * the last group name
     */
    private static final String THE_LAST_GROUP = "[0-9]";

    /**
     * Create the specified command and initialise it with
     * the given command argument.
     * @param argumentInput argument input as expected by the extending subclass.
     *
     * @throws IllegalArgumentException if given arguments are invalid
     * @throws NullPointerException if any of the given parameters are null.
     */
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
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

        if (argumentInput.equals("AUTHOR") || argumentInput.equals("TITLE")) {
            this.argumentInput = argumentInput;
            return true;
        }
        return false;
    }

    /**
     * execute group command and print the result to console
     * @param data book data to be considered for command execution.
     */
    @Override
    public void execute(LibraryData data) {
        if (data == null || argumentInput == null) {
            throw new NullPointerException("no entry");
        }

        if (data.getBookData().size() == 0) {
            System.out.println("The library has no book entries.");
            return;
        }

        //switch to different methods
        System.out.println("Grouped data by " + argumentInput);
        switch (argumentInput) {
            case "TITLE": {
                showByTitles(data);
                break;
            }
            case "AUTHOR": {
                showByAuthors(data);
            }
        }
    }

    /**
     * show the grouped title list
     * @param data the input data
     */
    private void showByTitles(LibraryData data) {

        //initialise the required data
        HashMap<String, ArrayList<String>> groupedData = new HashMap<>();
        groupingByTitle(groupedData, data);

        String[] capitals = new String[groupedData.size()];
        for (int i = 0; i < groupedData.size() - 1; i++) {
            String capitalNow = Character.toString('A' + i);
            capitals[i] = capitalNow;
        }
        capitals[groupedData.size() - 1] = THE_LAST_GROUP;

        //display the contents with the second sorting
        for (int i = 0; i < groupedData.size(); i++) {
            ArrayList<String> books = groupedData.get(capitals[i]);
            if (books.size() != 0) {
                System.out.println("## " + capitals[i]);
                for (String book : books) {
                    System.out.println("	" + book);
                }
            }
        }
    }

    /**
     * assigning the input data by title
     * @param groupedData the thing that keep data
     * @param data the input data
     */
    private void groupingByTitle(HashMap<String, ArrayList<String>> groupedData, LibraryData data) {

        //initialise the required data
        ArrayList<String> sortedTitles = sortTitles(data);
        titleGroupedDataInitialising(groupedData);

        //adding data
        for (String title : sortedTitles) {
            boolean isAdded = false;
            for (String capitalLetter : groupedData.keySet()) {
                if (Character.toString(title.toUpperCase().charAt(0)).equals(capitalLetter)) {
                    groupedData.get(capitalLetter).add(title);
                    isAdded = true;
                }
            }
            if (!isAdded) {
                groupedData.get(THE_LAST_GROUP).add(title);
            }
        }
    }

    /**
     * sorting titles
     * @param data data input
     *
     * @return give back a sorted title list
     */
    private ArrayList<String> sortTitles(LibraryData data) {

        //initialise title
        ArrayList<String> sortedTitles = new ArrayList<>();
        for (BookEntry book : data.getBookData()) {
            sortedTitles.add(book.getTitle());
        }

        //sorting
        Collections.sort(sortedTitles);
        return sortedTitles;
    }

    /**
     * initialise capital and books or the grouped data variable
     * @param groupedData used to pass value
     */
    private void titleGroupedDataInitialising(HashMap<String, ArrayList<String>> groupedData) {
        int theNumberOfGroups = 27;
        for (int i = 0; i < theNumberOfGroups - 1; i++) {
            groupedData.put(Character.toString('A' + i), new ArrayList<>());
        }
        groupedData.put(THE_LAST_GROUP, new ArrayList<>());
    }

    /**
     * show the grouped author list
     * @param data input data
     */
    private void showByAuthors(LibraryData data) {

        //initialise the required data
        HashMap<String, ArrayList<String>> groupedData = new HashMap<>();
        groupingByAuthor(groupedData, data);

        //display the contents
        String[] sortedAuthors = sortAuthors(data);
        for (String author : sortedAuthors) {
            System.out.println("## " + author);
            for (String title : groupedData.get(author)) {
                System.out.println("	" + title);
            }
        }
    }

    /**
     * sorting authors
     * @param data the data input
     *
     * @return return sorted authors
     */
    private String[] sortAuthors(LibraryData data) {

        //initialise authors
        HashSet<String> authors = new HashSet<>();
        for (BookEntry book : data.getBookData()) {
            String[] authorsOfThisBook = book.getAuthors();
            Collections.addAll(authors, authorsOfThisBook);
        }

        //sorting
        String[] sortedAuthors = authors.toArray(new String[authors.size()]);
        Arrays.sort(sortedAuthors);
        return sortedAuthors;
    }

    /**
     * assigning the input data
     * @param groupedData the thing that keep data
     * @param data the input data
     */
    private void groupingByAuthor(HashMap<String, ArrayList<String>> groupedData, LibraryData data) {

        //initialise the required data
        String[] sortedAuthors = sortAuthors(data);

        //initialising and adding data
        for (String author : sortedAuthors) {
            groupedData.put(author, new ArrayList<>());

            //adding data to temp
            for (BookEntry book : data.getBookData()) {
                for (String authorFromData : book.getAuthors()) {
                    if (authorFromData.equals(author)) {
                        groupedData.get(author).add(book.getTitle());
                        break;
                    }
                }
            }

            //sorting for second time of temp
            Collections.sort(groupedData.get(author));
        }
    }
}
