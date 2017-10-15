import java.util.*;
import java.lang.*;
public class EngimaClient {

	public static void main(String[] args) {// call all methods and class
		Scanner input=new Scanner(System.in);		
		display();		
		try{
			do{
				System.out.println();
				Enigma e=new Enigma();
				e=setting(input);
				if(e.isRotorValid(e.getInner())&&e.isRotorValid(e.getMiddle())){
				    process(input,e);
				}
			}while(playAgain(input));
			
		}
		catch(StringIndexOutOfBoundsException e) {
			System.out.println("The invalid string to be decrypted has space between chars");
		}		
	}
	
	/**This is the method to display the basic engima information.
	 */
	public static void display(){//display basic engima information on how to enter valid input
		System.out.println("This program simulates a simple 3 rotor Engima machine");
		System.out.println("You can choose to use the default rotor setting,");
		System.out.println("  or you can define your own rotor setting");
		System.out.println("The valid rotor setting starts with a #,");	
		System.out.println("  followed by all chars from english alphbet(occuring once)");
		System.out.println("The valid string to be decrypted has no space between chars,");
		System.out.println("When decrypting, use # to represent space to avoid errors");
		System.out.println();
	}
	
	/**This is to check if the user wants to play again.
	 * @param console: Scanner input.
	 * @return: return a boolean, true to play gain or false to exit.
	 */
	public static boolean playAgain(Scanner console){// check to play again
		System.out.println();
		System.out.print("Do you want to play again:(yes to continue, anything else to exit:)");
		String s=console.nextLine();
		return s.equalsIgnoreCase("yes");
	}
	
	/**This is to call a Engima constructor.
	 * @param console: Scanner input.
	 * @return: return a new Engima object.
	 */
	public static Enigma setting(Scanner console){// get a new Engima object
		System.out.println("Please select from the menu below:");
		System.out.println("1. Use default rotor settings");
		System.out.println("2. Input custom rotor settings");
		System.out.println("Selection:");
		int nb=console.nextInt();	
		Enigma e=new Enigma();
		if(nb==1){
			System.out.println("Using default rotor settings");
			e=new Enigma();
			initialSettingSelection(e);
		}
		if(nb==2){
			System.out.println("Using custom rotor settings");
			e=askSetting(console);
			initialSettingSelection(e);			
		}
		return e;
	}
	
	/**This is to ask for custom rotor setting
	 * @param console: Scanner input.
	 * @return: return a new custom Engima setting.
	 */
	public static Enigma askSetting(Scanner console){// ask for custom rotor setting
		Enigma eTest=new Enigma();
		System.out.println("Enter the inner rotor string:");
		console.nextLine();
		String i=console.nextLine();
		while(!eTest.isRotorValid(i)){
			System.out.println("Invalid rotor setting");
			System.out.println("Enter the inner rotor string again:");
			i=console.nextLine();
		}		
		System.out.println("Enter the middle rotor string:");
		String m=console.nextLine();
		while(!eTest.isRotorValid(m)){
			System.out.println("Invalid rotor setting");
			System.out.println("Enter the middle rotor string again:");
			m=console.nextLine();
		}
	    return new Enigma(i,m);
	}
	
	/**This is to display initial rotor setting.
	 * @param e: The Engima of which setting to be displayed.
	 */
	public static void initialSettingSelection(Enigma e){// display initial rotor setting
		System.out.println();
		System.out.println("The Enigma model will use the following settings:");
		System.out.println(e.toString());
	}
	
	/**This is to ask the user for different input for processing.
	 * @param console: Scanner input.
	 * @param e: The Engima object to be processed.
	 */
	public static void process(Scanner console,Enigma e){//ask for different input to process
		System.out.println();
		System.out.println("Would you like to:");
		System.out.println("        1.Encrypt");
		System.out.println("        2.Decrypt");
		System.out.println("        3.Run Default Example");
		int x=console.nextInt();
		console.nextLine();
		String s="";	
		if(x==1){
			System.out.println("You have chosen to encrypt string");
			System.out.println("Enter the string you want to encrypt:");
			s=s+console.nextLine();	
			pick(x,s,e);
		}
		else if(x==2){
			System.out.println("You have chosen to decrypt string");
			System.out.print("Please enter the string you want to decrypt:");
			s=s+console.nextLine();	
			pick(x,s,e);
		}
		else if(x==3){
			s="Computer Programming is Lots of Fun";
			System.out.println("You have chosen to use the default string:"+s);
			pick(x,s,e);
		}
		else{	
			System.out.println("Invalid selection!");
		}
	}	
	
	/**This is the different process based on selection from the user.
	 * @param nb: integer selection to determine how to process
	 * @param q:  String to be processed
	 * @param e:  Engima object to be processed.
	 */
	public static void pick(int nb,String q,Enigma e){//process differently based on user selection		
		if(nb==1){
			String initialSetting=e.toString();
			System.out.println();
			String s1=e.encrypt(q);
			System.out.println("After encrypting but before resetting the rotors are:");
			System.out.println(e.toString());
			System.out.println("Your encrypted string is: "+s1);
			display1(initialSetting,e);
//			String s2=e.decrypt(s1);
//			System.out.println("If we now decode this string, we get: "+s2);
			display2(e);
		}
		if(nb==2){
			String initialSetting=e.toString();
			System.out.println();
			String s1=e.decrypt(q);
			System.out.println("After decrypting but before resetting the rotors are:");
			System.out.println(e.toString());
			System.out.println("Your decrypted string is: "+s1);
			display1(initialSetting,e);
//			String s2=e.encrypt(s1);
//			System.out.println("If we now encode this string, we get: "+s2);
			display2(e);
		}
		if(nb==3){
			String initialSetting=e.toString();
			System.out.println();
			String s1=e.encrypt(q);
			System.out.println("After encrypting but before resetting the rotors are:");
			System.out.println(e.toString());
			System.out.println("Your encrypted string is: "+s1);
			display1(initialSetting,e);
			String s2=e.decrypt(s1);
			System.out.println("If we now decode this string, we get: "+s2);
			display2(e);
		}
	}
	
	public static void display1(String s,Enigma e){// display the initial setting in the end
		System.out.println();
		System.out.println("Initially, the rotors are:");
		System.out.println(s);
		System.out.println();
	}
	
	/**This is to reset the rotor and display the setting after processing.
	 * @param e: Engima object to be reset. 
	 */
	public static void display2(Enigma e){// reset rotor after processing
		System.out.println();
		System.out.println("The Enigma model should now be set back at the original settings:");
		e.reset();
		System.out.println(e.toString());
	}	
}
