/*
  Program summary: GUI that allows entry of Order Information.
  
  Program details: 
  --Conatains name and address input areas.
  --Two buttons handle most of the user interaction.
  --Employs the GridBagLayout for the main shopping window.
  --ImageIcons are used to adorn the buttons.
  --Another image displays an appropriate company logo.
  --When the user presse the save button the current inforamtion in the name and address fields are saved
  to plain text file called order.txt, which is saved in the same directory as the program.
  --The name information sits by itself on the first line of the tile while the second line (and successive lines) will
  consist of the address.
  --Each line of the address from the JTextArea reside on a separate line in the file containing the order information.
  --During the save process, the title bar of the main window provides an appropriate status message and pauses
  to provide the user time to read the message.
  --When the clear button is pressed the two input fields are cleared of text.
  --Clicking on the close window control will just shut the application down.
  --Because the address field can consist of multiple lines, hitting enter in the name field should have no
  effect (unlike the password field).

  Limitations:
  --The image files used by the GUI must be stored in the same directory as the source code.
  --The program does not read order information from a file.
  --If the order file already exists then it will be overwritten without any warning when the user hits save.

  Version: April 11, 2021
  --Refactored GUI component placement into a reusable function.
  --Added the save button action listener.
  --Added the clear button action listener.

  Version: April 10, 2021
  --Initial version is created.
  --Added GUI components and arranged them in the JFrame container using GridBabLayout.
  --

*/
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.awt.Component;

public class MyOrderFrame extends JFrame implements ActionListener{
    private static final String DEFAULT_TITLE = "Order Information";
    private ImageIcon companyLogo;
    private JLabel companyLogoLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel addressLabel;
    private JScrollPane addressScrollPane;
    private JTextArea addressText;
    private JButton saveButton;
    private ImageIcon saveButtonIcon;
    private JButton clearButton;
    private ImageIcon clearButtonIcon;
    private GridBagLayout aLayout;
    private GridBagConstraints aConstraint;

    public MyOrderFrame(){
        setTitle(DEFAULT_TITLE);
        this.setSize(450,350);
        aLayout = new GridBagLayout();
        aConstraint = new GridBagConstraints();
        aConstraint.insets = new Insets(5, 5, 5, 5);
        aConstraint.fill = GridBagConstraints.HORIZONTAL;
        aConstraint.anchor = GridBagConstraints.NORTHWEST;
        
        createControls();

        addWidget(companyLogoLabel, 0, 0, 2, 1);//create company logo
        addWidget(nameLabel, 0, 1, 1, 1);//create name label     
        addWidget(nameTextField, 0, 2, 1, 1);//create name text field
        addWidget(addressLabel, 1, 1, 1, 1);//create address label
        addWidget(addressScrollPane, 1, 2, 1, 1);//create address scroll pane
        aConstraint.fill = GridBagConstraints.BOTH;//we want the following componenets to fill out the grid bag cell
        addWidget(saveButton, 0, 3, 1, 1);//Create the save button
        addWidget(clearButton, 1, 3, 1, 1);//create the clear button
        clearButton.addActionListener(this);//add action to the clear button
        addSaveButtonActionListener();//add action to the save button

        this.setLayout(aLayout);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
    }

    //This function adds action to the save button: to write the information entered in the Name and Address fields into a file
    private void addSaveButtonActionListener(){
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    setTitle("Saving information...");
                    pauseThred(1500);
                    setTitle(DEFAULT_TITLE);
                    FileWriter fw = new FileWriter("order.txt");
                    BufferedWriter buffer = new BufferedWriter(fw);
                    buffer.write(nameTextField.getText());
                    buffer.newLine();
                    buffer.write(addressText.getText());
                    buffer.close();                                                
                }
                catch (IOException exception) {
                    System.out.println("Error writing to file: "+exception.getMessage());
                }
            }
        });
    }

    private void createControls(){
        companyLogo = new ImageIcon("logo.png");
        companyLogoLabel = new JLabel("");
        companyLogoLabel.setSize(50,50);
        companyLogoLabel.setIcon(companyLogo);

        nameLabel = new JLabel("Name:");
        nameLabel.setSize(50, 50);

        nameTextField = new JTextField(10);
        nameTextField.setSize(100,100);

        addressLabel = new JLabel("Address:");
        addressLabel.setSize(50,50);

        addressText = new JTextArea("", 4, 10);
        addressScrollPane = new JScrollPane(addressText);

        saveButton = new JButton("Save");
        saveButtonIcon = new ImageIcon("saveButtonIcon.png");
        saveButton.setIcon(saveButtonIcon);

        clearButton = new JButton("Clear");
        clearButtonIcon = new ImageIcon("clearButtonIcon.png");
        clearButton.setIcon(clearButtonIcon);
    }

    private void addWidget(Component c, int x, int y, int w, int h){
        aConstraint.gridx = x;
        aConstraint.gridy = y;
        aConstraint.gridwidth = w;
        aConstraint.gridheight = h;
        aLayout.setConstraints(c, aConstraint);
        add(c);
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

    //this function initiates action to clear text entry boxes when the clear button is pressed
    public void actionPerformed(ActionEvent e) {
        nameTextField.setText("");
        addressText.setText("");        
    }
}
