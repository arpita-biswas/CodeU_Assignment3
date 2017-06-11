package CodeU_Assignment3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Assignment 3: Find all the dictionary words from a given grid, 
 * formed by starting from any cell and traversing using the adjacent cells only.
 * 
 * Assumption: isWord(String str) and isPrefix(String str) are given.
 * These are methods of the Dictionary class. 
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class Q1 {
	public enum Moves { N, NE, E, SE, S, SW, W, NW };
	
	/**
	 * getAllWords() finds all the "dictionary" words from a given "grid"
	 * (having "n_row" rows and "n_column" number of columns), 
     * formed by a valid grid traversal, that is,
     * starting from any cell and traversing using the adjacent cells only.
	 * 
	 * @param n_row
	 * @param n_column
	 * @param grid
	 * @param dictionary
	 * @return
	 */
	public static Set<String> getAllWords(int n_row, int n_column, char [][] grid, Dictionary dictionary){
		//if the dictionary is null or empty, then no words can be formed, return null
		if(dictionary == null || dictionary.size() == 0){
			return null;
		}
		
		//if the grid is null or empty, then also no words can be formed, return null
		if(grid == null || grid.length == 0){
			return null;
		}
		
		//if n_row and n_column does not match the actual dimension of the grid, return null
		if(grid.length != n_row || grid[0].length != n_column){
			return null;
		}
				
		//Create a HashSet that stores all the "dictionary" words that
		//can be formed using valid grid traversals.
		
		Set<String> allWords = new HashSet<String>();
		for(int i=0; i<n_row; i++){
			for(int j=0; j<n_column; j++){
				// for each cell, call the getAllWordsHelper() function to create all 
				//dictionary words creating by valid traversal starting from that cell
				ArrayList<GridPoint> visited = new ArrayList<GridPoint>();
				Set<String> temp = getAllWordsHelper(grid, dictionary, i, j, visited);
				//getAllWordsHelper() return null when no valid word is return starting from that cell
				//if it does not return null, we add the valid words in the set of allWords
				if(temp!=null){
					//System.out.println(i+","+j+": "+temp.toString());
					allWords.addAll(temp);
				}
				else{
					//System.out.println(i+","+j+": null");
				}
				//System.out.println("**************************");
			}
		}
	
		return allWords;
	}
	
	/**
	 * move() takes as input a cell, represented by (r,c) in the grid, and checks whether 
	 * the "direction" specified is a valid direction to find a longer word.
	 * It checks whether or not the direction specified leads to a location not in the grid.
	 * Finally, if the direction is valid, this function returns 
	 * the new cell obtained by taking this "direction" from (r,c).
	 * If invalid, it returns null.
	 * 
	 * @param r
	 * @param c
	 * @param n_row
	 * @param n_column
	 * @return
	 */
	private static GridPoint move(Q1.Moves direction, int r, int c, int n_row, int n_column){
		
		if(direction == Q1.Moves.N){
			if(r == 0 ){
				return null;
			}
			return new GridPoint(r-1, c);
		}
		if(direction == Q1.Moves.NE){
			if(r == 0 || c == n_column-1){
				return null;
			}
			return new GridPoint(r-1, c+1);
		}
		if(direction == Q1.Moves.E){
			if(c == n_column-1){
				return null;
			}
			return new GridPoint(r, c+1);
		}
		if(direction == Q1.Moves.SE){
			if(r == n_row-1 || c == n_column-1){
				return null;
			}
			return new GridPoint(r+1, c+1);
		}
		if(direction == Q1.Moves.S){
			if(r == n_row-1){
				return null;
			}
			return new GridPoint(r+1, c);
		}
		if(direction == Q1.Moves.SW){
			if(r == n_row-1 || c == 0){
				return null;
			}
			return new GridPoint(r+1, c-1);
		}
		if(direction == Q1.Moves.W){
			if(c == 0){
				return null;
			}
			return new GridPoint(r, c-1);
		}
		if(direction == Q1.Moves.NW){
			if(r == 0 || c == 0){
				return null;
			}
			return new GridPoint(r-1, c-1);
		}
		return null;
	}
	
	/** getAllWordsHelper() finds all words that can be formed with the prefix specified
	 * by the "visited" list.
	 * 
	 * @param grid
	 * @param dictionary 
	 * @param r : row number of the cell to be processed
	 * @param c : column number of the cell to be processed
	 * @param visited : A list of cells that forms the prefix
	 * @return
	 */
	private static Set<String> getAllWordsHelper(char [][] grid, Dictionary dictionary, int r, int c, ArrayList<GridPoint> visited){
		Set<String> allStr = new HashSet<String>();
		//System.out.println("At ("+r+","+c+")");
		String word = new String();
		
		//Create the "potential" prefix string from "visited" list
		for(int i=0; i<visited.size(); i++){
			word = word.concat(Character.toString(grid[visited.get(i).r][visited.get(i).c]));
		}
		//System.out.print(word+"->");
		//append this character (r,c) to the "potential" prefix
		word = word + Character.toString(grid[r][c]);
		//System.out.println(word);
		
		//If the "potential" prefix string is not a valid prefix in the dictionary,
		//then return an empty set of strings
		if(!dictionary.isPrefix(word)){
			//System.out.println("not a prefix");
			return allStr;
		}
		
		//If the "potential" prefix is indeed a prefix of some valid word,
		//then, append this cell (r,c) to the visited list
		//System.out.println("is a prefix");
		visited.add(new GridPoint(r,c));
		
		//If "potential" prefix is a valid word,
		//then add it to the list of allStr to be returned
		if(dictionary.isWord(word)){
			//System.out.println("is a word");
			allStr.add(word);
		}
		//System.out.println("allStr: "+allStr.toString());
		//Check if there is a longer word with the same prefix
		for(Q1.Moves direction: Q1.Moves.values()){
			GridPoint gp = move(direction, r, c, grid.length, grid[0].length);
			if(gp != null && !visited.contains(gp)){
				ArrayList<GridPoint> visited2 = new ArrayList<GridPoint>();
				visited2.addAll(visited);
				Set<String> moreWords = getAllWordsHelper(grid, dictionary, gp.r, gp.c, visited2);
				if(moreWords != null){
					allStr.addAll(moreWords);
				}
			}
		}
		//System.out.println(allStr.toString());
		return allStr;
	}

	public static void main(String[] args) {
		testNullDictionary();
		testEmptyDictionary();
		testNullGrid();
		testEmptyGrid();
		testInvalidRowColumnDimension();
		testOneCellGrid();
		testNonRepeatationOfCells();
		testAdjacentTraversal();
		testPrefixWord();//If prefix is a word, traversal should continue to look for a longer word
		testDepthFirstSearch(); //Test if the depth first search traversal is working properly
	}
	
	@Test
	public static void testNullDictionary() {
		Dictionary dictionary = null;
		char [][] grid = {{'A','A','R'},{'T','C','D'}};
		int n_row = grid.length;
		int n_column = grid[0].length;
	    assertNull(Q1.getAllWords(n_row, n_column, grid, dictionary));
	}
	
	@Test
	public static void testEmptyDictionary() {
		Dictionary dictionary = new Dictionary();
		char [][] grid = {{'A','A','R'},{'T','C','D'}};
		int n_row = grid.length;
		int n_column = grid[0].length;
	    assertNull(Q1.getAllWords(n_row, n_column, grid, dictionary));
	}
	
	@Test
	public static void testNullGrid() {
		HashSet<String> d = new HashSet<String>();
		d.add("HELLO");
		d.add("WORLD");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = null;
		int n_row = 0;
		int n_column = 0;
	    assertNull(Q1.getAllWords(n_row, n_column, grid, dictionary));
	    
	}
	
	@Test
	public static void testEmptyGrid() {
		HashSet<String> d = new HashSet<String>();
		d.add("HELLO");
		d.add("WORLD");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {};
		int n_row = 0;
		int n_column = 0;
	    assertNull(Q1.getAllWords(n_row, n_column, grid, dictionary));
	}
	
	@Test
	public static void testInvalidRowColumnDimension() {
		HashSet<String> d = new HashSet<String>();
		d.add("HELLO");
		d.add("WORLD");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A','B'},{'C','D'}};
		int n_row = -1;
		int n_column = 5;
	    assertNull(Q1.getAllWords(n_row, n_column, grid, dictionary));
	    
	    n_row = 1;
		n_column = 2;
	    assertNull(Q1.getAllWords(n_row, n_column, grid, dictionary));
	}
	
	@Test
	public static void testOneCellGrid() {
		HashSet<String> d = new HashSet<String>();
		d.add("A");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A'}};
		int n_row = 1;
		int n_column = 1;
		HashSet<String> result = new HashSet<String>();
		result.add("A");
	    assertEquals(result, Q1.getAllWords(n_row, n_column, grid, dictionary));	    
	}
	
	@Test
	public static void testNonRepeatationOfCells() {
		// The cells should be visited only once while forming a word. 
		HashSet<String> d = new HashSet<String>();
		d.add("ABA");
		d.add("AB");
		d.add("BA");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A','B'}};
		int n_row = 1;
		int n_column = 2;
		HashSet<String> result = new HashSet<String>();
		result.add("AB");
		result.add("BA");
	    assertEquals(result, Q1.getAllWords(n_row, n_column, grid, dictionary));	    
	}
	
	@Test
	public static void testAdjacentTraversal() {
		// The adjacent cells should only be visited while forming a word. 
		HashSet<String> d = new HashSet<String>();
		d.add("AFB");
		d.add("DFC");
		d.add("ABCDEF");
		d.add("ABCFED");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A','B','C'},{'D', 'E', 'F'}};
		int n_row = 2;
		int n_column = 3;
		HashSet<String> result = new HashSet<String>();
		result.add("ABCFED");
	    assertEquals(result, Q1.getAllWords(n_row, n_column, grid, dictionary));	    
	}
	
	@Test
	public static void testPrefixWord() {
		// If prefix is a word, traversal should not stop there
		HashSet<String> d = new HashSet<String>();
		d.add("ABC");
		d.add("ABCED");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A','B','C'},{'D', 'E', 'F'}};
		int n_row = 2;
		int n_column = 3;
		HashSet<String> result = new HashSet<String>();
		result.add("ABC");
		result.add("ABCED");
	    assertEquals(result, Q1.getAllWords(n_row, n_column, grid, dictionary));	    
	}
	
	@Test
	public static void testDepthFirstSearch() {
		// Test if the depth first traversal is working fine
		HashSet<String> d = new HashSet<String>();
		d.add("ABCF");
		d.add("ABCE");
		d.add("ABCFE");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A','B','C'},{'D', 'E', 'F'}};
		int n_row = 2;
		int n_column = 3;
		HashSet<String> result = new HashSet<String>();
		result.add("ABCF");
		result.add("ABCE");
		result.add("ABCFE");
	    assertEquals(result, Q1.getAllWords(n_row, n_column, grid, dictionary));	    
	}
	
	@Test
	public static void testExample() {
		HashSet<String> d = new HashSet<String>();
		d.add("CAT");
		d.add("CAR");
		d.add("CARD");
		d.add("CART");
		Dictionary dictionary = new Dictionary(d);
		char [][] grid = {{'A','A','R'},{'T','C','D'}};
		int n_row = 2;
		int n_column = 3;
		HashSet<String> result = new HashSet<String>();
		result.add("CAR");
		result.add("CAT");
		result.add("CARD");
		assertEquals(result, Q1.getAllWords(n_row, n_column, grid, dictionary));	}

}
