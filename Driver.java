/*
  Program summary: A simple shopping program simulation.
  
  Program details: 
  --When the program is first run a login dialog is presented to the user.
  --When the user enters the correct password an Order Information GUI is displayed.

  Limitations:
  --The password is easily accessible through a text file, so security is probably weak for this app.
  --The user only has three attempts to enter the password before the application is closed.

  Version: April 11, 2021
  --Instance of the MyPasswordFrame is initialized.

  Version: April 10, 2021
  --Initial version is created.

*/


public class Driver{
    public static void main(String [] args)    {
        MyPasswordFrame passwordFrame = new MyPasswordFrame();
    }
} 