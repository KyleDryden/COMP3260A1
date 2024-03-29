/* Classname: Analysis
 * Programmer: Kyle Dryden
 * Version: Java 14
 * Date: 16/03/2023
 * Description: Analysis tool used to perform the actual analysis from the files. Utilises a
 * 				hashmap to provide letter-value pairs.
 */

package primary;

import java.util.HashMap;

public class Analysis {
	
	private String testString; //String thats provided from the files.
	
	/* String-Integer paired HashMap used to pair the letters found with their occurrence values. Used for
	 * single-letter substitution, amongst diagram and trigram findings.
	 */
	private HashMap<String, Integer> occurrenceValues;
	private String alphabet;
	
	//Constructor that gets a string sent to it.
	public Analysis(String testString) {
		this.testString = testString;
		occurrenceValues = new HashMap<String, Integer>();
		alphabet = "abcdefghijklmnopqrstuvwxyz";
	}
		
	//Public method that finds the how many of each ciphertext letters there are.
	public HashMap<String, Integer> letterFrequency() {
		String[] splitString = testString.split("");
		for (int i=0; i<testString.length(); i++) {
			if (splitString[i].contains(" ") || splitString[i].contains("\n")) {
				continue;
			}
			
			String testLetter = splitString[i];
			
			if (occurrenceValues.get(testLetter) == null) {
				occurrenceValues.put(testLetter, 1);
			}
			
			else {
				occurrenceValues.put(testLetter, occurrenceValues.get(testLetter) + 1);
			}
		}
		
		return occurrenceValues;
	}
	
	//Public method that checks a specific index of each period, given a period is seen to exist in the cipher.
	public HashMap<String, Integer> periodFrequency(int index) {
		String[] splitString = testString.split(" ");
		for (int i=0; i<splitString.length; i++) {
			String testLetter = splitString[i].substring(index, index+1);
			
			if (occurrenceValues.get(testLetter) == null) {
				occurrenceValues.put(testLetter,  1);
			}
			
			else {
				occurrenceValues.put(testLetter, occurrenceValues.get(testLetter) + 1);
			}
		}
		
		return occurrenceValues;
	}
	
	public String shiftWords(String keyword) {
		String resultString = "";
		String[] splitString = testString.split(" ");
		int[] indexes = new int[keyword.length()];
		
		for (int i=0; i<keyword.length(); i++) {
			char testChar = keyword.charAt(i);
			
			indexes[i] = alphabet.indexOf(testChar);
		}
			
		for (int i=0; i<splitString.length; i++) {
			
			String periodString = "";
			
			for (int m=0; m<splitString[i].length(); m++) {
				String replaceChar = "";
				int replaceIndex = (alphabet.indexOf(splitString[i].substring(m, m+1)) + indexes[m]) % 26;
				replaceChar = alphabet.substring(replaceIndex, replaceIndex+1);
				periodString += replaceChar;
			}
			
			resultString += periodString + " ";
		}
		
		return resultString;
	}
	
	public double indexOfCoincidence(int N) {
		double returnValue = 0.0;
		
		for (String i : occurrenceValues.keySet()) {
			int Fi = occurrenceValues.get(i);
			
			int topValue = Fi * (Fi-1);
			int bottomValue = N * (N-1);
			returnValue += (double) topValue / bottomValue;
		}
		
		return returnValue;
	}
}
