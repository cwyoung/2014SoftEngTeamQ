
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


public class MainFrame  extends JFrame{
    private JPanel MainPanel;
    private JPasswordField passwordField;
    private JTextField VoterID;
    private JButton loginButton;
    private JLabel accessCodeTextPane;

    private String out;
   // public String AccessCode;




    public static void main(String[] args){
       JFrame frame = new JFrame("MainFrame");
       frame.setContentPane(new MainFrame().MainPanel);
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.pack();
       frame.setVisible(true);
    }

    public MainFrame(){
        super("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vID = VoterID.getText();
                out = vID;
                char [] pw = passwordField.getPassword();
                if(checkPass(pw)&& (!vID.isEmpty())){
                  WriteToFile();
                  new VotingFrame();

                //dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please recheck your access code and make sure you entered YOUR voter ID!","Incorrect Access Code",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


// get password
    public char[] getPassword(){
        BufferedReader input;
        String AccessCode =  null;
        try {
            input = new BufferedReader(new FileReader("accessCode.txt"));
            AccessCode = input.readLine();
            //  System.out.println("access code: " + AccessCode);
            input.close();
        }catch(IOException e){
            e.getStackTrace();
        }
        return AccessCode.toCharArray();
    }
//check password
    public boolean checkPass(char[] pass){
        char[] RealPass = getPassword();
        if(pass.length != RealPass.length)
            return false;
        for (int i =0; i < pass.length; i++)
                   if (pass[i] != RealPass[i])
                       return false;
        return true;
    }

 //write to file
    public void WriteToFile (){
        BufferedWriter output;
        String tempID;
        try{
            output = new BufferedWriter(new FileWriter("tempVID.txt"));
            tempID = out;
            output.write(tempID);
            output.close();
        } catch(IOException e){
            System.out.println("Couldn't create a file");
        }
    }

}
