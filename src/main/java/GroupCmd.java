import java.awt.print.Book;
import java.lang.management.BufferPoolMXBean;
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
		String[] sortedTitles = sortTitles(data);
		
		HashMap<String, ArrayList<String>> grouping = new HashMap<>();
		constructCapitalMap(grouping, sortedTitles);
		
		int theNumberOfGroups = 27;
		for(int i = 0; i < theNumberOfGroups - 1; i++) {
			String capitalNow = Character.toString('A' + i);
			ArrayList<String> books = grouping.get(capitalNow);
			if(books.size() != 0) {
				System.out.println("## " + capitalNow);
				for(String j : books) {
					System.out.println("	" + j);
				}
			}
		}
	}
	
	private String[] sortTitles(LibraryData data) {
		ArrayList<String> theTitle = new ArrayList<>();
		for(BookEntry i : data.getBookData()) {
			theTitle.add(i.getTitle());
		}
		String[] sortedKeyMap = theTitle.toArray(new String[theTitle.size()]);
		Arrays.sort(sortedKeyMap);
		return sortedKeyMap;
	}
	
	private void constructCapitalMap(HashMap<String, ArrayList<String>> grouping, String[] sortedTitles) {
		
		//initialise capital and books
		int theNumberOfGroups = 27;
		for(int i = 0; i < theNumberOfGroups - 1; i++) {
			grouping.put(Character.toString('A' + i), new ArrayList<>());
		}
		grouping.put("[0-9]", new ArrayList<>());
		
		//adding data
		for(String i : sortedTitles) {
			for(String j : grouping.keySet()) {
				if(Character.toString(i.toUpperCase().charAt(0)).equals(j)) {
					grouping.get(j).add(i);
				}
			}
			grouping.get("[0-9]").add(i);
		}
		
	}
	
	private void showByAuthors(LibraryData data) {
		String[] sortedAuthors = sortAuthors(data);
		
		HashMap<String, String[]> groupedData = new HashMap<>();
		grouping(groupedData, sortedAuthors, data);
		
		for(String i : sortedAuthors) {
			System.out.println("## " + i);
			for(String j : groupedData.get(i)) {
				System.out.println("	" + j);
			}
		}
	}
	
	private void grouping(HashMap<String, String[]> groupedData, String[] sortedAuthors, LibraryData data) {
		
		HashMap<String, ArrayList<String>> tempGroup = new HashMap<>();
		
		//adding data
		for(String i : sortedAuthors) {
			tempGroup.put(i, new ArrayList<>());
			for(BookEntry j : data.getBookData()) {
				for(String authorFromData : j.getAuthors()) {
					if(authorFromData.equals(i)) {
						tempGroup.get(i).add(j.getTitle());
						break;
					}
				}
			}
			String[] temp = tempGroup.get(i).toArray(new String[tempGroup.get(i).size()]);
			Arrays.sort(temp);
			groupedData.put(i,temp);
		}
	}
	
	private String[] sortAuthors(LibraryData data) {
		//initialise authors and books
		HashSet<String> authors = new HashSet<>();
		for (BookEntry i : data.getBookData()){
			String[] authorsOfThisBook = i.getAuthors();
			Collections.addAll(authors, authorsOfThisBook);
		}
		
		String[] sortedAuthors = authors.toArray(new String[authors.size()]);
		Arrays.sort(sortedAuthors);
		return sortedAuthors;
	}
	
}
