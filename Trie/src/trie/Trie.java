package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		
		if (allWords.length == 0) {
			TrieNode emptyRoot = new TrieNode(null,null,null);
			return emptyRoot;
		}
		
		TrieNode emptyRoot = new TrieNode(null,null,null);		// emptyRoot, ALWAYS empty (only thing it can have  is a firstChild)
		
		//Insert first index into trie (REGARDLESS) 
		emptyRoot.firstChild = new TrieNode(new Indexes(0,(short)0,(short)(allWords[0].length()-1)),null,null);
		
		// Now we need to loop through each new word (substring), and for each char in the word,
		// look at previous array indexes to see if they have similar prefixes.
		
		
		//this is just a big linked list?
		TrieNode ptr  = emptyRoot.firstChild;
		TrieNode prev = emptyRoot.firstChild;
		

		int first      =-1;
		int last       =-1;
		int letterLoc  =-1;
		int findPrefix =-1;
		for(int i = 1; i < allWords.length; i++) {
		// go thru
			while(ptr != null) {
				letterLoc  = ptr.substr.wordIndex;
				last       = ptr.substr.endIndex;
				first      = ptr.substr.startIndex;

				//continue if index is shorter than our pointers
				if( first > allWords[i].length() ) {
					prev = ptr;
					ptr = ptr.sibling;
					continue; //SKIP ITERATION
				}
				
				int subIt = 0;
				while(subIt < allWords[letterLoc].substring(first,last+1).length() &&  
					  subIt < allWords[i].substring(first).length() &&
					  allWords[i].substring(first).charAt(subIt) == 
					  allWords[letterLoc].substring(first,last+1).charAt(subIt)) 
				{
					subIt = subIt +1;
				}
				findPrefix = subIt-1;
				
				
				
				if (findPrefix != -1 ) {
					findPrefix += first;
					}
				
				if(findPrefix == -1 ) { 
					prev = ptr;
					ptr = ptr.sibling;
					}
				
				
				
				else {
					if( findPrefix == last ) { 
						prev = ptr;
						ptr = ptr.firstChild;
					}
					else if (findPrefix < last){
						prev = ptr;
						break;
						}
					
				}
			}
			
			
			if(ptr == null  ) {
				Indexes newTriplet = new Indexes(i, (short)first, (short)(allWords[i].length()-1));
				prev.sibling = new TrieNode(newTriplet, null, null);
				
				
			} //check the Node file for the methods that I can use
			else {
				TrieNode prevFirstChild = prev.firstChild;
				TrieNode childofPrev = prev.firstChild; 
				
				
				Indexes fullWord = new Indexes(prev.substr.wordIndex, (short)(findPrefix+1), prev.substr.endIndex); //find indexes
				prev.substr.endIndex = (short)findPrefix; 						// set end to findPrefix
				prev.firstChild = new TrieNode(fullWord, null, null);			 //null it
				prev.firstChild.firstChild = childofPrev;						 // use the placeholder, for some reason can only double firstChild?
				prev.firstChild.sibling = new TrieNode(new Indexes((short)i, (short)(findPrefix+1), (short)(allWords[i].length()-1)), null, null);

			}

			
			
			
			//this is how we iterate (restart) 
			findPrefix = -1;				
			first = -1;
			prev = emptyRoot.firstChild;
			last = -1; 
			ptr =  emptyRoot.firstChild;
			letterLoc = -1;

		}
		// FOLLOWING LINE IS A PLACEHOLDER TO ENSURE COMPILATION
		// MODIFY IT AS NEEDED FOR YOUR IMPLEMENTATION
		//if (breakCondition ==false)
			//return null;
		return emptyRoot;
	}
	

	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		if (root == null)
			return null;
		
		
		TrieNode rootPtr = root;
		ArrayList<TrieNode> found = new ArrayList<>();

		while (rootPtr !=null){
			if (rootPtr.substr == null) {
				TrieNode rootPtrChild = rootPtr.firstChild;
				rootPtr = rootPtr.firstChild;
			}
			
			if ((prefix.startsWith(allWords[rootPtr.substr.wordIndex].substring(0,rootPtr.substr.endIndex+1)) || allWords[rootPtr.substr.wordIndex].startsWith(prefix))) {
				
					if (rootPtr.firstChild != null) {
						
						found.addAll(completionList(rootPtr.firstChild,allWords,prefix));
						rootPtr = rootPtr.sibling;
					
					}
					else {
						found.add(rootPtr);
						rootPtr = rootPtr.sibling;
					}
				
			}
			else {
				rootPtr = rootPtr.sibling;
			}
		}

		
		
		if (found.size() == 0)
			return null;
		
		return found;
	}
	

	
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
