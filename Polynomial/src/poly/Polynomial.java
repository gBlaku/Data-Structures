package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node addPoly = new Node(0,0,null);// This is head, need to remove eventually
		Node polyOne = poly1;
		Node polyTwo = poly2;
		Node frontofPoly = addPoly;		// head ref
		
		
		while (polyOne !=null || polyTwo != null) {
			
			if (polyOne == null) {
				addPoly.next = new Node(0,0,null);
				addPoly = addPoly.next;
				addPoly.term.coeff  = polyTwo.term.coeff;
				addPoly.term.degree = polyTwo.term.degree;
				polyTwo = polyTwo.next;
				System.out.println("This node (one is NULL) is "+ addPoly.term);
				continue;
			}
			
			else if (polyTwo ==null) {
				addPoly.next = new Node(0,0,null);
				addPoly = addPoly.next;
				addPoly.term.coeff  = polyOne.term.coeff;
				addPoly.term.degree = polyOne.term.degree;
				polyOne = polyOne.next;
				System.out.println("This node (two is NULL) is "+ addPoly.term);
				continue;
			}
			
			
			if (polyOne.term.degree > polyTwo.term.degree) {	//Poly 1 > Poly 2
				addPoly.next = new Node(0,0,null);
				addPoly = addPoly.next;
				addPoly.term.coeff  = polyTwo.term.coeff;
				addPoly.term.degree = polyTwo.term.degree;
				polyTwo = polyTwo.next;
				System.out.println("This node (one > two) is "+ addPoly.term);
				//continue;
			}
			else if (polyTwo.term.degree > polyOne.term.degree) {	//Poly2 > Poly1
				addPoly.next = new Node(0,0,null);
				addPoly = addPoly.next;
				addPoly.term.coeff  = polyOne.term.coeff;
				addPoly.term.degree = polyOne.term.degree;
				polyOne = polyOne.next;
				System.out.println("This node (two > one) is "+ addPoly.term);
				//continue;
			}
			else if (polyTwo.term.degree == polyOne.term.degree) {	//Poly 1 = Poly2
				
				if (polyTwo.term.coeff + polyOne.term.coeff !=0) {	//no 0 coeff!!!
					addPoly.next = new Node(0,0,null);
					addPoly = addPoly.next;
					addPoly.term.coeff  = polyTwo.term.coeff + polyOne.term.coeff;
					addPoly.term.degree = polyOne.term.degree; 		// polyTwo.termDegree??
					polyOne = polyOne.next;
					polyTwo = polyTwo.next;
					System.out.println("This node (EQUAL) is "+ addPoly.term);
					
				}
				
				else if (polyTwo.term.coeff + polyOne.term.coeff == 0) {				// if the numbers add to 0 SKIP AND DO NOT PUT IN LL
					polyOne = polyOne.next;
					polyTwo = polyTwo.next;
					
				
				}	
			}
		}
		frontofPoly = frontofPoly.next;		//remove 0x^0 HEAD
		// reverseLL(frontofPoly);				// REVERSE THE LINKED LIST to ascending since prints in reverse lol
		return frontofPoly;					
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		//polynomial multiplication so.. multiply each term of poly1 to ALL terms of poly2 
	
		Node multPoly, answer, poly2Ref, poly1Test, poly2Test;
		poly1Test = poly1; 
		multPoly  = null;
		poly2Test = poly2;
		answer    = null;
		poly2Ref = poly2;
		
		if (poly1Test==null || poly2Test==null) {
			return new Node(0,0,null);
		}
		
		while (poly1Test!=null) {
			while (poly2Test!=null) {
				float polyCoeff  = poly1Test.term.coeff  * poly2Test.term.coeff;
				int   polyDegree = poly1Test.term.degree + poly2Test.term.degree;
				
				multPoly = new Node(polyCoeff,polyDegree, multPoly);
				//multPoly = multPoly.next;
				poly2Test = poly2Test.next;
			}
			Node reversedMultPoly = reverseLL(multPoly);
			answer = add(answer,reversedMultPoly);
			
			poly2Test = poly2Ref;
			poly1Test = poly1Test.next;
			multPoly = null;
		}
		
					
		return answer;
		
	}
	
	private static Node reverseLL (Node node) {
		Node finalNode, next, currentNode;
		finalNode = null;
		next = null;
		currentNode = node; //Test
		
		while (currentNode !=null) {
			next = currentNode.next;
			currentNode.next = finalNode;
			finalNode = currentNode;
			currentNode = next;
		}
		return finalNode;
		
	}

	
	
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		float polyAtGivenX = 0;					
		for (Node polyList = poly; polyList !=null; polyList = polyList.next) {
			polyAtGivenX += (polyList.term.coeff * (float)Math.pow(x, polyList.term.degree));
		}
		return polyAtGivenX;			//RETURNS FLOAT, MAKE SURE ALL IS FLOAT 
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
