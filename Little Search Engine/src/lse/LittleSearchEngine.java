package lse;

import java.io.*; 

import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords    = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		if (docFile ==null)
			throw new FileNotFoundException();
		
		HashMap<String, Occurrence> hash = new HashMap<String,Occurrence>();	//all words go here
		Scanner scanDoc = new Scanner(new File(docFile));
		
		
		if(scanDoc.hasNext() ) {
			do {
				String nextKey = getKeyword(scanDoc.next()); //iterate
				if (nextKey != null) {
					
				
					if (hash.containsKey(nextKey)) {		// if hash has more...
						Occurrence nextKeyInfo = hash.get(nextKey);  		
						nextKeyInfo.frequency +=1; //up frequency
					}
					else {
						Occurrence nextKeyInfo = new Occurrence(docFile,1); //if it doesnt have more elements
						//nextKeyInfo.frequency +=1;	//do we need this?			
						hash.put(nextKey,nextKeyInfo);		
					
					}
				}
			
			}while (scanDoc.hasNext());	
		}
		scanDoc.close();
		
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return hash;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
			for (String nextKey : kws.keySet()) {
				ArrayList<Occurrence> OccurrencesofWord = new ArrayList<Occurrence>();
				
				
				if (keywordsIndex.containsKey(nextKey)) {
					OccurrencesofWord = keywordsIndex.get(nextKey);		//first key is first in arrayList
				}
				
				OccurrencesofWord.add(kws.get(nextKey));	//keep adding keys to arrayist 
				
				 
				insertLastOccurrence(OccurrencesofWord);		// send clone into function
				keywordsIndex.put(nextKey,OccurrencesofWord);				// put key with occurrences, merged
				
				}
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation(s), consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * NO OTHER CHARACTER SHOULD COUNT AS PUNCTUATION
	 * 
	 * If a word has multiple trailing punctuation characters, they must all be stripped
	 * So "word!!" will become "word", and "word?!?!" will also become "word"
	 * 
	 * See assignment description for examples
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		
		if (word == null ) 
			return null;
		
		if (word.equals(null))
			return null;
		
		if (Character.isLetter(word.charAt(0)) == false )
			return null;
		
		if (word.length() <=0)
			return null;
		
		String lowerCase = word.toLowerCase();
		char finalChar   = lowerCase.charAt(lowerCase.length()-1); // final char of lowercase word string
		
		
		if (    trailPunc(finalChar) == true) {		// if it has punc at end
			do {
					if (finalChar == '(' || finalChar == ')')
						return null;
					if (finalChar == '.' || finalChar == ',' || finalChar == '?' || finalChar == ':'
						|| finalChar == ';' || finalChar == '!') { // only do this if it's valid punc
					
				lowerCase = lowerCase.substring(0, lowerCase.length()-1);	// cut it off
				
				if (lowerCase.length() <= 1)				// check for empty array
					break;
				else
					finalChar = lowerCase.charAt(lowerCase.length()-1);		// nonPunc char, BAM
					
					}
					
					else // else it is not VALID PUNC, then return null
						return null;
			} while ( trailPunc(finalChar) == true);
		}
		
		
		// now we have to check if the words have anything BUT letters in between (in which case null)
		
		if (digitCheck(lowerCase) == true || puncCheck(lowerCase) == true 		// controversial
				|| noiseWords.contains(lowerCase)) {
			
			return null;	
			}			// also check if it has noise words, cut dem out!!! 
		
		
		
			
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		
		return lowerCase; // return modified word (with no trailing punc)
	}
	
	private static boolean trailPunc(char end) {		// check last index in string for punc (trailing)
		boolean kekX;
		if (Character.isLetter(end) == false)
			kekX = true;
		else 
			kekX = false;
		
		return kekX;
	}
	
	
	
	
	
	private static boolean digitCheck(String string) {	//check for digig from last to first
		int k = string.length()-1;
		if (k>-1 ) {
			do {
				char kChar = string.charAt(k);
				if (Character.isDigit(kChar))
					return true;
				k --;
		} while (k > 0 );								// only do this when k > 0, or else you have an empty string.
			
	}
		return false;
	}
	
	
	
	
	// loop through word backwords, check if it has any mid-punctuation, like the - in mid-punctuation lol
	private static boolean puncCheck(String string) { //check for punc from last to first
		int k = string.length()-1;
		if (k>-1) {
			do {
				char kChar = string.charAt(k);				// start at last char in the string, word --> d
				if (!Character.isLetterOrDigit(kChar))		// if current char is not alph letter, return null
					return true;
				k --;
		} while (k > 0 );									// only do this when k > 0, or else you have an empty string.
			
	}
		return false;
	}
		
	
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		
ArrayList<Integer> mValues = new ArrayList<Integer>(); 
		
		// pretty much binary search lol
		if(occs.size()==1)
		{
			return null;
		}
		
		
		Occurrence tar = occs.get(occs.size()-1);
		occs.remove(occs.size()-1);
		
		
		int center=0;
		int last = occs.size()-1;
		int centerFreq;
		int first = 0;
		
		
		
		
		
		if (first <= last) {
			do
		
		{
			center = (last+first)/2;
			centerFreq=occs.get(center).frequency;
			
			
			if(centerFreq==tar.frequency)
			{
				mValues.add(center);
				break;
			}
			
			if(centerFreq <tar.frequency)
			{
				last=center-1;
				mValues.add(center);
			}
			
			if(centerFreq>tar.frequency)
			{
				first=center+1;
				mValues.add(center);
				center+=1;

			}
		}while(last >=first);
			
		}
		
		
		
		
		occs.add(center,tar);
		
		
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		return mValues;
		
		
	}
	
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. 
	 * 
	 * Note that a matching document will only appear once in the result. 
	 * 
	 * Ties in frequency values are broken in favor of the first keyword. 
	 * That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2 also with the same 
	 * frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * See assignment description for examples
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, 
	 *         returns null or empty array list.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Occurrence> bothWords = new ArrayList<Occurrence>();
		ArrayList<Occurrence> firstWord = new ArrayList<Occurrence>();
		ArrayList<Occurrence> secondWord = new ArrayList<Occurrence>();
		
		if (keywordsIndex.containsKey(kw2)) {
			
			secondWord = keywordsIndex.get(kw2);
		}
		if (keywordsIndex.containsKey(kw1)) {
			
			firstWord = keywordsIndex.get(kw1);
		}
	
		bothWords.addAll(firstWord); 			
		bothWords.addAll(secondWord);
		
		ArrayList<String> theWords = new ArrayList<String>();

		if (firstWord.isEmpty() && secondWord.isEmpty()) {
				int p = 2;
			}

		else if ( secondWord.isEmpty() == false && firstWord.isEmpty() == false  ) {
			int i = 0;
			int j = 0;
			while ((i+1)<bothWords.size()) {
				j = 1;
				while (bothWords.size() > (j+i)) {
					int prevFreq = bothWords.get(j-1).frequency;
					int currFreq = bothWords.get(j).frequency;
					if (currFreq > prevFreq ) {
						Occurrence previousYeet = bothWords.get(j-1);
						Occurrence jValYeet     = bothWords.get(j);
						bothWords.set(j-1, jValYeet);
						bothWords.set(j, previousYeet);
					}
					j = j+1;
				}
				i = i+1;
			}
			
			int k = 0;
			int l = 0;
			while (bothWords.size() > (k+1)) {
				l = k+1; 													// after k in all cases
				while (bothWords.size() > l) {
					if ((bothWords.get(k).document == bothWords.get(l).document)) {
						bothWords.remove(l);								// remove first occ of l
					}
						l = l+1;
				}
				k = k+1;
			}
			
		}	
				
		//Check for more than 5 docs
			int size = bothWords.size();
			if (size > 5) {
				do {
					int lastOcc = bothWords.size()-1;
					bothWords.remove(lastOcc);		// get rid of extras
					size --;
					
				} while (size > 5);
			}	
		
		
		
		// add docs of the ~descending order to this arraylist
		for (Occurrence kekCheck : bothWords) {		    // for each occ in bothWrods
			theWords.add(kekCheck.document);			// Words should be sorted, no duplicates, ADD now!
		}
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return theWords;
	
	}
}
