/*
  Program summary: GUI that asks user for a password.
  
  Program details: 
  --JPasswordField is used to sotre the input.
  --The password to login to the program is stored in an encrypted format in a file called password.txt.
  --The program is able to use any arbitrary password file.
  --The program reads the encrypted password and converts it to the original form.
  --The user entered password is compared to the enencrypted password stored in the password.txt file.
  --The password is checked when the user hits the login button or if the user hits enter when the cursor is acrive 
  in the password field of the GUI.
  --The the entered password is not correct than a suitable error message as well as the current number of attempts 
  and the maximum number allowed is shown in the title bar.
  --If three unsuccessful attempts have been nade then a suitable error message should be provided and the program will end.
  --The proagram will pause so the user actually has time to see error messages before it shuts down.
  --Clicking on the close window of the login dialog (or the main shoping window) will also end the program, although without an error message.
  --If the password has been correctly entered then the login window is banished and the main shopping screen comes up.

  Limitations:
  --The password.txt file is not checked to only contain one password
  --The password.txt file consists of the string "password" after the Saesar cipher has shifted each character backward one place in the alphabet.
  --The program assumes that the password.txt file only consists of lower case alphabetic characters.

  Version: April 11, 2021
  --Added a function to decypher the encrpted password that is stored in a file.
  --Added a function to compare the stored password and the user enetered password.
  --Added functionality to count user atttempts and display attempt informations.
  --Added functionality to call MyOrderFrame instance when the user enters a correct password.
  --Added an action listener that responds to user button click or enter key hit.

  Version: April 10, 2021
  --Initial version is created.
  --Added GUI components and arranged them in the JFrame window.

*/

import javax.swing.JPasswordField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class MyPasswordFrame extends JFrame implements ActionListener{
    private JPasswordField aPasswordField;
    private JButton aButton;
    private String storedPassword;//this variable will hold the password that is stored in our encrypted text file
    private int loginAttemptCounter;

    public MyPasswordFrame(){
        JLabel aLabel = new JLabel("Enter password:");
        aPasswordField = new JPasswordField();
        aPasswordField.addActionListener(this);
        aPasswordField.setBounds(180,50,110,20);
        aLabel.setBounds(190,30,100,20);
        setLayout(null);
        add(aPasswordField);
        add(aLabel);
        setTitle("Login screen");//set an initial Title Bar value
        aButton = new JButton("Login");
        aButton.setBounds(160,75,150,20);
        aButton.addActionListener(this);
        add(aButton);
        setBounds(100,100,450,200);
        setVisible(true);//make all the GUI componenets visible
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        storedPassword = readStoredPassword("password.txt");//read and decrypt the password that is stored in a file
        loginAttemptCounter = 0;//initialize the number of login attempts left
    }

    //This functions reads the stored password in a text file and returns it as a string
    private String readStoredPassword(String filePath){
        
        String readLine = "";//stores a whole line read from a file
        BufferedReader br = null;//create a buffered reader object
        FileReader fr = null;//create a file reader object
        
        //Read data from a file
        try{
	        fr = new FileReader(filePath);//instantiate a file reader oject
            br = new BufferedReader(fr);//instantiate a buffered reader object
            readLine = br.readLine();//read the first line from the filePath location\

            if (readLine == null)//check if the read line is empty
                System.out.println("Empty file, nothing to read");

            if (readLine != null)//decrypt the read line if it is not empty
                readLine = decryptStoredPassword(readLine);

            fr.close();//close the file that is being read from
	    }
        catch (FileNotFoundException e){
            System.out.println("Could not open "+filePath);
	    }
        catch (IOException e){
            System.out.println("Trouble reading from "+filePath);
	    }
        
        return readLine;//return the last non-empty line that was read from the file
    }
    
    //this function decrypts the stored password using the Caesar cypher
    private String decryptStoredPassword(String encryptedPassword){
        
        char encryptedPasswordArray[] = encryptedPassword.toCharArray();//convert the encryptedPassword string to an array of characters
        char decryptedPasswordArray[] = new char[encryptedPasswordArray.length];//this array will store the decrypted characters
        int shift = 1;//this value indicates by how much the stored password has to shift to be decrypted

        for (int i=0; i<encryptedPasswordArray.length;i++){//iterate through each element in the ch1 character array
            if(Character.isLetter(encryptedPasswordArray[i]))//if a letter then do this
                decryptedPasswordArray[i] = (char)(((int)encryptedPasswordArray[i]+shift-97)%26+97);
            else if (encryptedPasswordArray[i] == ' ')//if a space then do this
                decryptedPasswordArray[i] = ' ';           
        }
        
        //use the String object constructor to conver the decrypted password array into a string
        String decryptedPassword = new String(decryptedPasswordArray);
        return decryptedPassword;//return the string of the decrypted password
    }

    //This function checks if the user entered a correct password and moves them into the order information page if they entered a correct password
    public void actionPerformed(ActionEvent e){
        final int MATCH = 0;//a constant that lets us know if the entered password matches the stored decrypted password

        //store the value that is entered into the JPasswordField in the GUI
        String passWordEntered = new String(aPasswordField.getPassword());

        if (passWordEntered.compareTo(storedPassword) == MATCH){
            loginSuccess();
        }
        else{
            loginFailed();
        }
    }

    //This function updates the title bar if user enters a wrong password and exits the program if maximum number of attempts are exceeded
    public void loginFailed(){
        loginAttemptCounter++;//increment the login attempt counter
        final int DELAY = 1500;//specify a delay time
	    setTitle("Password is incorrect.");//set a temporary title bar message
        pauseThred(DELAY);//pause the program
        setTitle("No. of incorrect login attempts (max=3): "+loginAttemptCounter);//update the title bar message with attempts remaining
        aPasswordField.setText("");//clear the password field since the entered value is wrong

        if(loginAttemptCounter > 2){//do this if the max login attempts are exceeded    
            pauseThred(DELAY);
            setTitle("Max attempts exceeded, exiting...");//inform the user that the progrma is exiting
            pauseThred(DELAY);
            //close the dialog window and exit the program            
            setVisible(false);
            dispose();
        }    
    }

    //this function pauses the thread that is executing the program for a specified amount of time
    private void pauseThred(int delayTime){
        try{
	        Thread.sleep(delayTime);//pause the program for a specified amount of time
	    }
        catch (InterruptedException ex){ 
	        System.out.println("Pausing of program was interrupted");//catch an exception if interruption of the program could not be performed
	    }
    }

    //This function moves from the login screen to the main shopping screen
    public void loginSuccess(){
        //Inform the user via the title bar that their login attemp was successful
        final int DELAY = 3000;
	    setTitle("Login successful!");
	    try{
	        Thread.sleep(DELAY); 
	    }
	    catch (InterruptedException ex){ 
	        System.out.println("Pausing of program was interrupted");
    	}
    
        setVisible(false);
        dispose();

        //open the order GUI 
        MyOrderFrame orderFrame = new MyOrderFrame();
        orderFrame.setVisible(true);
    }
}