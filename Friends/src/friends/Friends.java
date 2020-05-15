package friends;

import java.util.ArrayList;   

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */
	
	
	
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		//Person first = g.members[g.map.get(p1)];
		//Person last  = g.members[g.map.get(p2)];
		int iterate = 0;
		if (p1.equals(p2)) {
			return null;
		}
	
		
		if (p1 == null || p2 == null || g == null) {
			return null;
		}
		
		ArrayList<String> theShortestPossiblePath = new ArrayList<String>();
		
		
					// TEST --> REMOVE
		theShortestPossiblePath = bfsShortestChain(g,p1,p2,false, iterate);
		
		
		
		
		return theShortestPossiblePath;
		
		//System.out.println(first);
		//System.out.print(last);;
		//DFS(first,last);
		//return null;
	}
	
	
	private static ArrayList<String> bfsShortestChain(Graph g, String fromHere, String toHere,
			boolean check, int counter){
	
		// whole bunch of initialization 
		int gLength = g.members.length;
		ArrayList<String> peopleInWay = new ArrayList<String>();
		Queue<Person> peopleQueue = new Queue<Person>();
		Person[] backTrack    = new Person[gLength];
		Person[] backTrackTwo = new Person[gLength/2];		// may be unnecessary
		boolean[] seenAlready = new boolean[gLength];
		
		
		seenAlready[g.map.get(fromHere)] = true;
		peopleQueue.enqueue(g.members[g.map.get(fromHere)]);

		

		if (peopleQueue.isEmpty()==false) {
		do {			//bfs while not empty
			
			
			Person first  = peopleQueue.dequeue();
			String name   = first.name;
			Friend friend = first.first;
			
			
			if (friend == null)
				return null;
	
			int    fpos   = friend.fnum;
			seenAlready[g.map.get(name)] = true;
			
			
			if (fpos != friend.fnum)
				return null;
			
			// if (friend == null &&  == false) {return null;}
			
			 if (friend !=null){
				do {
					if (!seenAlready[friend.fnum]) {
						backTrack[friend.fnum] = first; 
						peopleQueue.enqueue(g.members[friend.fnum]);		// contv
						seenAlready[friend.fnum] = true;
						
						
						
						
						if (g.members[friend.fnum].name.equals(toHere)) {
							first = g.members[friend.fnum];
						
							while (!first.name.equals(fromHere) ) {
								peopleInWay.add(0, first.name);
								first = backTrack[g.map.get(first.name)];
								}
							
							
							peopleInWay.add(0, fromHere);
							return peopleInWay;
							}
						
						
						}
					friend = friend.next;
					
				}while (friend != null); 
			 }
	
			} while (peopleQueue.isEmpty() == false); 
		}
	
		
	 return null;	// else null
				
			
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null or empty array list if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		if (school == null || g == null)
			return null;
		ArrayList<ArrayList<String>> cliqueArrofArr= new ArrayList<ArrayList<String>>();
	
	
		
		boolean[] seenAlready = new boolean[g.members.length];
		ArrayList<String> kidsInClique = new ArrayList<String>();
		Queue<Person> bfsQ = new Queue<Person>();

		
		Person firstPerson = g.members[0];
		cliqueArrofArr =  recursiveBreadth(cliqueArrofArr,  seenAlready, g, school, firstPerson);
		
		return cliqueArrofArr;
	}
	
	private static ArrayList<ArrayList<String>> recursiveBreadth(ArrayList<ArrayList<String>> cliqueArrofArr,boolean[] seenAlready,Graph graph,String personSchool, Person kid  ){
		
		
		Queue<Person> bfsQ = new Queue<Person>();
		Person firstKid = new Person();
		ArrayList<String> kidsinClique = new ArrayList<String>();
		Friend first;

		bfsQ.enqueue(kid);
		
		int kidIndex = graph.map.get(kid.name);
		seenAlready[kidIndex] = true;		// always visited first graph node
		
		
		
		
		
		if (kid.school == null || kid.school.equals(personSchool) == false) {
			bfsQ.dequeue();
			
			//Check if it's already seen
			int jSeen = 0;
			while (jSeen < seenAlready.length ) {
				if (seenAlready[jSeen] == false ) {
					return recursiveBreadth(cliqueArrofArr,seenAlready,graph,  personSchool, graph.members[jSeen]);
					}
				jSeen+=1;
				}
			
		}
		
		 
		while (bfsQ.isEmpty() == false ) {
			//Initialize
			firstKid = bfsQ.dequeue();
			first = firstKid.first;
			kidsinClique.add(firstKid.name);
			
			
		
			 if (first!=null ) {
			 do {
				 
				int fNum = first.fnum;
				if (seenAlready[fNum] == false) {
					
					if (  graph.members[fNum].school == null) {
						// do nothing lol
					}
					else {	// queue it up
						if (graph.members[fNum].school.equals(personSchool) ) 
							bfsQ.enqueue(graph.members[fNum]);
						}
					
					
						seenAlready[fNum] = true;
					
				
					}
					first = first.next;
			 	} while (first!=null );
			 }
		}	
		if (cliqueArrofArr.isEmpty() == false  && kidsinClique.isEmpty())
			return null;
		
		else 
			cliqueArrofArr.add( kidsinClique);
		
		int foundIndex = 0;
		while (foundIndex < seenAlready.length) {
			if (seenAlready[foundIndex] == true) //nothing
			if (seenAlready[foundIndex] == false) 
				return recursiveBreadth(cliqueArrofArr,seenAlready,graph,  personSchool, graph.members[foundIndex]);
		
			foundIndex +=1;
		}
		
		//ArrayList<ArrayList<String>> cliqueArrofArrs = cliqueArrofArr;
		return cliqueArrofArr;
	}

	

	
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null or empty array list if there are no connectors.
	 */	
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		if (g == null) {
			return null;
		}
		
		ArrayList<String> onTheWay = new ArrayList<String>();
		
		boolean didCheck = true;
		int gLength = g.members.length;
		int[] before = new int[(int)gLength];
		int[] depth= new int[(int)gLength];
		boolean[] alreadySeen = new boolean[(int)gLength];
		ArrayList<String> arrOfConn = new ArrayList<String>();
		int iterate = 0;
		
		
		
		
		int index = 0; 		// dfs on all unseen nodes
		while (index < gLength ){
			if (alreadySeen[index] == false ) {
				int empty0s[] = new int[]{0,0};
				arrOfConn = DFS(depth,  g.members[index], didCheck, empty0s, alreadySeen, 
						  before, onTheWay,arrOfConn,   g, iterate);
			}
			index+=1;
		}
			return arrOfConn;
	}
	
	private static ArrayList<String> DFS(int[] depth, Person firstKid, boolean check,
										 int[] empty0s, boolean[] seenAlready,  int[] before,
										 ArrayList<String> trackThePath, ArrayList<String> arrOfConn
										,   Graph g, int counter){
											
		
		Friend friendOfKid;						
		friendOfKid     = firstKid.first;
		String kidName  = firstKid.name;
		int kidLocation = g.map.get(kidName);
		
		
		
		
		before[kidLocation]      = empty0s[1];
		seenAlready[kidLocation] = true;
		depth[kidLocation] 		 = empty0s[0];
		
		
		if (friendOfKid!= null ) {
		do {
			
			if (seenAlready[friendOfKid.fnum] == false ) {
				int Pos1 = empty0s[1];
				int Pos0 = empty0s[0];
				
				Pos1 = Pos1 +1;
				Pos0 = Pos0 +1;
				
				
				arrOfConn = DFS( depth, g.members[friendOfKid.fnum], false,empty0s,
									seenAlready, before, trackThePath, arrOfConn, g, 0);
				
				if (  before[friendOfKid.fnum] >= depth[g.map.get(firstKid.name)]) {
					
					if ( (check == false && arrOfConn.contains(firstKid.name) == false) || (trackThePath.contains(firstKid.name) && arrOfConn.contains(firstKid.name) == false)) {
						
						arrOfConn.add(firstKid.name);
					}
				}
				else {
					
					int intialKid;
					int nextKid;
					nextKid      = before[friendOfKid.fnum];
					intialKid    = before[g.map.get(firstKid.name)];
				
					
					if (nextKid > intialKid )
						before[g.map.get(firstKid.name)] = intialKid;
							
					else 
						before[g.map.get(firstKid.name)] = nextKid;
					
					
				}		
			
				trackThePath.add(firstKid.name);
			}
			else {
				
				int kidThree;
				int kidFour;
				kidThree = before[g.map.get(firstKid.name)];
			    kidFour  = depth[friendOfKid.fnum];
			    
		
				if (kidFour > kidThree  ) 
					before[g.map.get(firstKid.name)] = kidThree;
				else
					before[g.map.get(firstKid.name)] = kidFour;
			 }
			
			friendOfKid = friendOfKid.next;
			
		} while (friendOfKid != null);
	}
		return arrOfConn;
	}
	
	
	
	
	
} // entire file end
		
		
		

		
	


