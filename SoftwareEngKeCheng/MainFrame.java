
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


public class MainFrame  extends JFrame{
    private JPanel MainPanel;
    private JPasswordField passwordField;
    private JTextField electionID;
    private JButton loginButton;
    private JLabel accessCodeTextPane;

    private String out;




    public static void main(String[] args){
       JFrame frame = new JFrame("MainFrame");
       frame.setContentPane(new MainFrame().MainPanel);
       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       frame.pack();
       frame.setVisible(true);
       frame.setLocationRelativeTo(null);
    }

    public MainFrame(){
        super("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vID = electionID.getText();
                out = vID;
                char [] pw = passwordField.getPassword();
                if(checkPass(pw)&& (!vID.isEmpty())){
                  WriteToFile();
                  new VotingFrame();

                //dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Please recheck your Election ID and make sure you entered the corresponding access code!","Incorrect Access Code",JOptionPane.ERROR_MESSAGE);
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
        String tempEID;
        try{
            output = new BufferedWriter(new FileWriter("tempEID.txt"));
            tempEID = out;
            output.write(tempEID);
            output.close();
        } catch(IOException e){
            System.out.println("Couldn't create a file");
        }
    }

}
