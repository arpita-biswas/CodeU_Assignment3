# CodeU_Assignment3
Given a grid of letters and a dictionary, find all the words from the dictionary that can be formed in the grid.  
The rules for forming a word:  
● You can start at any position.  
● You can move to one of the 8 adjacent cells (horizontally/vertically/diagonally).  
● You can't visit the same cell twice in the same word.  


Assume you have a dictionary class with these two methods:  
● isWord(string): Returns whether the given string is a valid word.  
● isPrefix(string): Returns whether the given string is a prefix of at least one word in the dictionary.  

The function receives the number of rows, number of columns, a 2-dimensional array of characters (of the native char data type), and the dictionary. Return the set of all words found.
