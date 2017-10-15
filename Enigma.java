import java.util.Arrays;
/**
 * This is to code all the methods required to simulate Engima machine
 * @author wj
 */

public class Enigma {
	// required instance members go here
	// for example, all three rotors are declared here
	// instance member to keep countClock to move middle rotor

	public static final String outerRotor = "#BDFHJLNPRTVXZACEGIKMOQSUWY";
	public static final String middleRotor ="#EJOTYCHMRWAFKPUZDINSXBGLQV";
	public static final String innerRotor = "#GNUAHOVBIPWCJQXDKRYELSZFMT";
	private String outer;
	private String middle;
	private String inner;
	private int countClock;
	private int countAntiClock;
	private String middleRe;
	private String innerRe;
	
    /**Constructs a default constructor.
     * Rotor settings are default settings.
     * Instance members to move middle rotor are set to be 0
     */
	public Enigma() {
		// default constructor - constructs enigma machine as shown in spec
		outer = outerRotor;
		middle = middleRotor;
		inner = innerRotor;
		countClock = 0;
		countAntiClock = 0;
		middleRe=middleRotor;
		innerRe=innerRotor;
	}
    
	/**Constructs an argument constructor.
     * Rotor settings are default settings.
     * Instance members to move middle rotor are set to be 0
     */
	public Enigma(String s1, String s2) {
		// non-default constructor - constructs machine with user specified
		// inner and middle rotors
		// non-default constructor should call method(s) to make sure rotors
		// meet requirements
		outer = outerRotor;
		inner = s1;
		middle = s2;
		countClock = 0;
		countAntiClock = 0;
		innerRe=s1;
		middleRe=s2;		
	}
    /**This is to verify the custom input setting is valid. 
     * @param rotStr: represents the custom input setting.
     * It has to start with #,followed with all chars from English alphbets occurring only once.   
     * @return: a boolean, true for a valid string or false for a invalid string.
     */
	public boolean isRotorValid(String rotStr) {
		// verify that rotStr is exactly 27 chars long
		// verify that all chars from english alphbet occur only once
		// verify that rotor starts with a # char
		if (!(rotStr.length() == 27)) {
			return false;
		}
		if (!rotStr.startsWith("#")) {
			return false;
		}
		String s = (rotStr.replace("#", "")).toUpperCase();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (!Character.isLetter(ch)) {
				return false;
			}
		}
		char[] chars = s.toCharArray();
		Arrays.sort(chars);
		String sorted = new String(chars);
		if (!sorted.equals("ABCDEFGHIJKLMNOPQRSTUVWXYZ")) {
			return false;
		}
		return true;
	}
    /**This is to encrypt a string based on special rules.
     * @param message: the string to be encrypted.
     * @return : return a encrypted string. 
     */
	public String encrypt(String message) {
		// call to encodeChar
		// call to rotateClockwise
		String encodes = "";
		for (int i = 0; i < message.length(); i++) {
			encodes += encodeChar((message.toUpperCase()).charAt(i));
			rotateClockwise();
			countClock++;
			if (countClock% 27==0) {
				middle = middle.substring(middle.length() - 1)
				+ middle.substring(0, middle.length() - 1);
			}
		}
		return encodes;
	}

	/**This is to decrypt a string based on special rules.
	 * @param message: the string to be decrypted.
	 * @return: return a encrypted string. 
	 */
	public String decrypt(String message) {
		// call to rotateAntiClockwise
		// call to decodeChar
		String decodes = "";
		for (int i = message.length() - 1; i >= 0; i--) {
			rotateAntiClockwise();
			int nb = message.length() / 27 * 27;
			if (countAntiClock < message.length()) {
				countAntiClock++;
				for (int j = nb; j >= 1; j--) {
					if (countAntiClock == message.length() - j * 27+1) {
						middle = middle.substring(1) + middle.substring(0, 1);
					}
				}
			}
			decodes = decodeChar((message.toUpperCase()).charAt(i)) + decodes;
		}
		return decodes;
	}


	/**This is to encrypt a char based on special rules.
	 * @param ch: the char to be encrypted.
	 * @return: return a encrypted char. 
	 */
	public char encodeChar(char ch) {
		// this finds the code character for ch (as per spec)
		if (ch == ' ') {
			ch = '#';
		}
		int InnerPosition = inner.indexOf(ch);
		char outChar = outer.charAt(InnerPosition);		
		int middlePosition = middle.indexOf(outChar);		
		return outer.charAt(middlePosition);
	}

	/**This is to decrypt a char based on special rules.
	 * @param ch: the char to be decrypted.
	 * @return: return a encrypted char.
	 * It returns a ' ' if a encrypted char to represent a space. 
	 */
	public char decodeChar(char ch) {
		// this finds the original character for the code ch (as per spec)
		int outPosition = outer.indexOf(ch);
		char middleChar = middle.charAt(outPosition);
		int outPosition2 = outer.indexOf(middleChar);
		char output = inner.charAt(outPosition2);
		if (output=='#'){
			output=' ';
		}
		return output;
	}

	/**This is to rotate the inner rotor clockwise once.
	 * The last char in the inner rotor will be put in the beginning 
	 * while all other chars will move to the right by one place.
	 */
	public void rotateClockwise() {
		inner = inner.substring(inner.length() - 1)
				+ inner.substring(0, inner.length()-1);
	}

	/**This is to rotate the inner rotor counetr clockwise once.
	 * The first char in the inner rotor will be put in the end 
	 * while all other chars will move to the left by one place.
	 */
	public void rotateAntiClockwise() {
		inner = inner.substring(1) + inner.substring(0,1);
	}

	/**This is to reset all rotors to the initial setting.
	 * It will be default setting or custom initial setting.
	 */
	public void reset() {
		// resets to align all # chars on all rotors (returns rotors to initial
		// configuration)
		outer = outerRotor;
		inner = innerRe;
		middle = middleRe;
		countClock = 0;
		countAntiClock = 0;
	}

	/**This is to get the inner rotor setting
	 * @return: inner setting
	 */
	public String getInner() {
		return inner;
	}

	/**This is to get the middle rotor setting
	 * @return: middle setting
	 */
	public String getMiddle() {
		return middle;
	}
	
	/**This is to get the outer rotor setting
	 * @return: outer setting
	 */
	public String getOuter() {
		return outer;
	}

	/**This is to get the rotors setting.
	 * @return: rotor settings string
	 */
	public String toString() {
		return "        Outer: " + outer + "\n" + "        Middle: " + middle
				+ "\n" + "        Inner: " + inner;
	}
}
