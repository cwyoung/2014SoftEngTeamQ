

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.*;
import java.lang.String;
import java.util.Scanner;
import javax.swing.DefaultListModel;




 public class VotingFrame extends JFrame implements ListSelectionListener{
    private JButton Exit;
    private JButton Vote;
    private JPanel VotingPanel;
    private DefaultListModel listModel;
    private JList list;
    private String key ="TeamQ";


    public static void main(String[] args){
        VotingFrame frame = new VotingFrame();

    }
    public void valueChanged (ListSelectionEvent e){
        //do nothing
    }

//creating a canadate list
    public VotingFrame(){
        super("Voting");
       // setSize(450,550);
        getContentPane().add(VotingPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
       setExtendedState(JFrame.MAXIMIZED_BOTH);

        //set the list
        BufferedReader input;
        String lName;
        //  ArrayList<String> list = new ArrayList<String>();
        listModel = new DefaultListModel();
        try {
            File file = new File("Election.txt");
            Scanner scanner = new Scanner(file);
            int i = 1;
            while (scanner.hasNextLine()) {
                String names = scanner.nextLine();
                listModel.addElement( names);

            }
            // System.out.println(list);
            list.setModel(listModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //exit button
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    System.exit(0);
            }
        });

        //Vote Button.
        Vote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String c = list.getSelectedValue().toString();
                WriteFile(c);
                delete();
                //Decryption();
                JOptionPane.showMessageDialog( null,"Thanks for voting! Have a good day!!");
                System.exit(0);
            }
        });
}

    public void delete(){
        try{
            File file = new File("tempVID.txt");
            file.delete();
        }catch (Exception e){
            System.out.println("Couldn't delete a file");
        }
    }

    public void WriteFile(String who){
        BufferedWriter output;
        BufferedReader input;
        String vID = "";
        try {
            input = new BufferedReader(new FileReader("tempVID.txt"));
            vID = input.readLine();
            input.close();
        }catch(IOException e){
            e.getStackTrace();
        }
        try {
            output = new BufferedWriter(new FileWriter("ElectionResult.txt",true));
            String result = (encrypt(vID+";"+who));
            output.write(result);
            output.newLine();
            output.close();
            //testing decryption  it works
            String abc = decrypt(result);
            System.out.println("the decrypted: "+abc );
        }catch(IOException e2){
            e2.getStackTrace();
        }
    }
     public String  encrypt(String a)
     {  StringBuffer sb = new StringBuffer((a));
         int resultLen = a.length();
         int keyLen = key.length();
         for (int i = 0, j =0; i<resultLen; i++ ,j++)
         {
             if (j >= keyLen) j =0;
             sb.setCharAt(i, (char) ((a.charAt(i)^key.charAt(j))));
         }
         return sb.toString();
     }
     public String decrypt(String a)
     {
         return encrypt(a);
     }

 }

