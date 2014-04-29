
import javax.crypto.spec.SecretKeySpec;
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
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;



 public class VotingFrame extends JFrame implements ListSelectionListener{
    private JButton Exit;
    private JButton Vote;
    private JPanel VotingPanel;
    private DefaultListModel listModel;
    private JList list;
    private byte[] saveKey;


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
            byte[] name = Encryption(who);
            byte[] id = Encryption(vID);
            output.write(id+":"+name);
            output.newLine();
            output.close();
            //testing decryption

        }catch(IOException e2){
            e2.getStackTrace();
        }
    }
     //Using examples from mkyong
    public byte[] Encryption(String a)
    {
        try{
            //here we create a key.
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey key = keygenerator.generateKey();
            saveKey = key.getEncoded();

            //saving the key into a file for other time uses
            BufferedWriter output;
            try {
                output = new BufferedWriter(new FileWriter("Key.txt",true));
                String saveKeyString = saveKey.toString();
                output.write(saveKeyString);
                output.newLine();
                output.close();
                //testing decryption

            }catch(IOException e){
                e.getStackTrace();
            }

            //create the cipher
            Cipher cipher;
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = a.getBytes();
            byte[] Encrypt = cipher.doFinal(result);
            return Encrypt;
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch(NoSuchPaddingException e){
            e.printStackTrace();
        }catch(InvalidKeyException e){
            e.printStackTrace();
        }catch(IllegalBlockSizeException e){
            e.printStackTrace();
        }catch(BadPaddingException e){
            e.printStackTrace();
        }
        return null;
    }

     //Decryption
     public void Decryption(byte[] id){
    //retrieve the key
         try{
         SecretKey getKey;
         getKey = new SecretKeySpec(saveKey,"DES");
         Cipher desCipher;
         desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
         desCipher.init(desCipher.DECRYPT_MODE, getKey); //using the key we created in encryption
             //grabbing  name
         byte[] DecryptedID = desCipher.doFinal(id);
             //print
         System.out.println(new String(DecryptedID));
     }catch(NoSuchAlgorithmException e){
         e.printStackTrace();
     }catch(NoSuchPaddingException e){
         e.printStackTrace();
     }catch(InvalidKeyException e){
         e.printStackTrace();
     }catch(IllegalBlockSizeException e){
         e.printStackTrace();
     }catch(BadPaddingException e){
         e.printStackTrace();
     }

     }
}

