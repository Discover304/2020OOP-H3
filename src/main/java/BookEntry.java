import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable class encapsulating data for a single book entry.
 */
public class BookEntry {

    /**
     * the lowest rating
     */
    public final int LOWEST_RATING = 0;

    /**
     * the highest rating
     */
    public final int HIGHEST_RATING = 5;

    /**
     * the lowest number of page
     */
    public final int LOWEST_PAGE_NUMBER = 0;

    /**
     * this is the title of the book
     */
    private final String title;

    /**
     * this is the authors of the book
     */
    private final String[] authors;

    /**
     * this is the rate of the book
     */
    private final float rating;

    /**
     * this is the ISBN of the book
     */
    private final String ISBN;

    /**
     * this is the total pages of the book
     */
    private final int pages;

    /**
     * Constructor initialise all fields
     * @throws NullPointerException if the entry has a null parameter
     * @throws IllegalArgumentException if the entry has a not acceptable entry
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        if (title == null || authors == null || ISBN == null) {
            throw new NullPointerException("no book entry");
        }

        if (pages < LOWEST_PAGE_NUMBER || rating < LOWEST_RATING || rating > HIGHEST_RATING) {
            throw new IllegalArgumentException("invalid entry");
        }

        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;
    }

    /**
     * get the title
     * @return string title
     */
    public String getTitle() {
        return title;
    }

    /**
     * get the author
     * @return Array of authors
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * get the rating of the book
     * @return float number
     */
    public float getRating() {
        return rating;
    }

    /**
     * this will return the ISBN code of the book
     * @return String ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * get the total pages of the book
     * @return integer value pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * give a String statement of this book entry class
     * @return a formatted String including the information or fields of this class
     */
    @Override
    public String toString() {
        return title +
                "\nby " + authorsToString() +
                "\nRating: " + String.format("%.2f", rating) +//rating need to be formatted to 2 decimal places
                "\nISBN: " + ISBN +
                "\n" + pages + " pages";//using word pages or page is not concerned by us
    }

    /**
     * helper function of toString method
     * @return a String of the authors
     */
    private String authorsToString() {
        StringBuilder theString = new StringBuilder();
        for (String author : authors) {
            theString.append(author).append(", ");
        }
        theString.delete(theString.length() - 2, theString.length());//eliminate the last two ", "
        return theString.toString();
    }

    /**
     * see if two book entry are actually equal
     * @param o the object waiting to be compared
     *
     * @return boolean value to see if the two book are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookEntry bookEntry = (BookEntry) o;
        return Float.compare(bookEntry.rating, rating) == 0 &&
                pages == bookEntry.pages &&
                Objects.equals(title, bookEntry.title) &&
                Arrays.equals(authors, bookEntry.authors) &&
                Objects.equals(ISBN, bookEntry.ISBN);
    }

    /**
     * the hash code
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(title, rating, ISBN, pages);
        result = 31 * result + Arrays.hashCode(authors);
        return result;
    }

}
