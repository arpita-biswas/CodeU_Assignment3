
import java.util.HashSet;
import java.util.Set;

/**
 * Implemented Dictionary with the following methods:
 * 1. isWord(String str): checks if str is a valid dictionary word
 * 2. isPrefix(String str): checks if str is prefix of some valid word in the dictionary
 *
 */
public class Dictionary {
	
	Set<String> dictionary;	
	
	public Dictionary(){
		this.dictionary = new HashSet<String>();
	}
	public Dictionary(HashSet<String> d){
		this.dictionary = d;
	}
	
	public boolean isWord(String str){
		return this.dictionary.contains(str);
	}
	
	public boolean isPrefix(String str){		
		for(String s: this.dictionary){
			if(s.startsWith(str)){
				return true;
			}
		}
		return false;
	}
	
	public int size(){
		return this.dictionary.size();
	}

}
