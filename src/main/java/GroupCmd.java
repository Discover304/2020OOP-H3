import com.sun.source.tree.BreakTree;
import com.sun.tools.jconsole.JConsoleContext;

import javax.print.attribute.standard.PresentationDirection;
import java.lang.reflect.Array;
import java.util.*;

public class GroupCmd extends LibraryCommand {
	
	private String argumentInput;
	
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
	
	@Override
	protected boolean parseArguments(String argumentInput) {
		if(argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		if(argumentInput.equals("")) {
			return false;
		}
		
		argumentInput = argumentInput.trim();
		
		if(argumentInput.equals("AUTHOR") || argumentInput.equals("TITLE")) {
			this.argumentInput = argumentInput;
			return true;
		}
		return false;
	}
	
	@Override
	public void execute(LibraryData data) {//todo this is a total mass
		if(data == null || argumentInput == null) {
			throw new NullPointerException("no entry");
		}
		
		if(data.getBookData().size() == 0) {
			System.out.println("The library has no book entries.");
			return;
		}
		
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
	
	private void showByTitles(LibraryData data) {
		HashMap<String, BookEntry> titleAndBooks = new HashMap<>();
		HashMap<String, ArrayList<BookEntry>> grouping = new HashMap<>();
		
		constructTitleMap(titleAndBooks, data);
		constructCapitalMap(grouping, titleAndBooks);
		
		int theNumberOfGroups = 27;
		for(int i = 0; i < theNumberOfGroups - 1; i++) {
			String capitalNow = Character.toString('A' + i);
			ArrayList<BookEntry> books = grouping.get(capitalNow);
			if(books.size() != 0) {
				System.out.println("## " + capitalNow);
				for(BookEntry j : books) {
					System.out.println("	" + j.getTitle());
				}
			}
		}
		grouping.put("[0-9]", new ArrayList<>());
		
	}
	
	private void constructTitleMap(HashMap<String, BookEntry> titleAndBook, LibraryData data) {
		ArrayList<String> theTitle = new ArrayList<>();
		for(BookEntry i : data.getBookData()) {
			theTitle.add(i.getTitle());
		}
		String[] sortedKeyMap = theTitle.toArray(new String[theTitle.size()]);
		Arrays.sort(sortedKeyMap);
		
		for(String i : sortedKeyMap) {
			for(BookEntry j : data.getBookData()) {
				if(j.getTitle().equals(i)) {
					titleAndBook.put(i, j);
					break;
				}
			}
		}
	}
	
	private void constructCapitalMap(HashMap<String, ArrayList<BookEntry>> grouping, HashMap<String, BookEntry> titleAndBook) {
		
		//initialise capital and books
		int theNumberOfGroups = 27;
		for(int i = 0; i < theNumberOfGroups - 1; i++) {
			grouping.put(Character.toString('A' + i), new ArrayList<>());
		}
		grouping.put("[0-9]", new ArrayList<>());
		
		//adding data
		for(String i : titleAndBook.keySet()) {
			for(String j : grouping.keySet()) {
				if(Character.toString(i.toUpperCase().charAt(0)).equals(j)) {
					grouping.get(j).add(titleAndBook.get(i));
				}
			}
			grouping.get("[0-9]").add(titleAndBook.get(i));
		}
		
	}
	
	private void showByAuthors(LibraryData data) {
		HashMap<String, BookEntry> titleAndBooks = new HashMap<>();
		HashMap<String, ArrayList<BookEntry>> grouping = new HashMap<>();
		
		constructTitleMap(titleAndBooks, data);
		constructFinalMap(grouping, titleAndBooks);
		
		for(String i : grouping.keySet()) {
			System.out.println("## " + i);
			for(BookEntry j : grouping.get(i)) {
				System.out.println("	" + j.getTitle());
			}
		}
	}
	
	private void constructFinalMap(HashMap<String, ArrayList<BookEntry>> grouping, HashMap<String, BookEntry> titleAndBook) {
		String[] sortedAuthors = getSortedAuthors(titleAndBook);
		
		//adding data
		for(String i : sortedAuthors) {
			grouping.put(i, new ArrayList<>());
			for(String j : titleAndBook.keySet()) {
				for(String authorFromData : titleAndBook.get(j).getAuthors()) {
					if(authorFromData.equals(i)) {
						grouping.get(i).add(titleAndBook.get(j));
						break;
					}
				}
			}
		}
	}
	
	private String[] getSortedAuthors(HashMap<String, BookEntry> titleAndBook) {
		//initialise authors and books
		HashSet<String> authors = new HashSet<>();
		for (String i : titleAndBook.keySet()){
			String[] authorsOfThisBook = titleAndBook.get(i).getAuthors();
			Collections.addAll(authors, authorsOfThisBook);
		}
		
		String[] sortedAuthors = authors.toArray(new String[authors.size()]);
		Arrays.sort(sortedAuthors);
		return sortedAuthors;
	}
	
}
