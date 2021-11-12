/*
  Program summary: Uses a recursive function to ask for user age until a valid age is entered.
  
  Program details: 
  --Employs a simple recursive implementation to error check user input.
  --As long as the value entered is outside the vaid range of 1-144 the program keeps asking for a valid age.
  --After getting a valid age the program will display in main the value entered by the user.
  -The final value retured by the promotForAge method must be withing the valid range.

  Limitations:
  --A static method is used so theh traditions of OOO designed are not followed for this program.

*/

import java.util.Scanner;

public class RecursiveDriver
{
    public static final int MIN_AGE = 1;
    public static final int MAX_AGE = 144;
    public static Scanner in = new Scanner(System.in);

    public static int promptForAge()
    {
        int enteredAge = 0;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter a valid age: ");
        enteredAge = in.nextInt();
        
        //verify the entered age and either call another instance of the function or return a valid age value
        if (enteredAge>0 && enteredAge <145)
            return enteredAge;
        else
            return(promptForAge());
    }

    public static void main(String [] args)
    {
        int age = 0;      
        age = promptForAge();  
        System.out.println("Age: " + age);        
    }
}