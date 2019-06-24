/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contactsapp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author aggelos
 */
public class MainFrame extends JFrame {
       
    private JTextField nameTF, mobileTF, emailTF;
    private JButton saveBtn, addBtn, clearBtn, loadBtn, exitBtn;
    private JTextArea area;
    
    private ArrayList<Contact> contactsList;

    //alt+ins --> Ctrl+i

    public MainFrame() {
        
        //1. handle window closing action
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //2. create UI components        
        createUI();        
        
        //3. add functionality to buttons
        
        //3.1 instantiate list
        contactsList = new ArrayList();
        
        //3.2 add contact
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //receive the input 
                String name = nameTF.getText().trim();
                String mobile = mobileTF.getText().trim();
                String email = emailTF.getText().trim();
                
                //check the input
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Insert name", "Field Missing", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                if (mobile.isEmpty()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Insert mobile", "Field Missing", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Insert email", "Field Missing", JOptionPane.ERROR_MESSAGE); 
                    return;
                }
                
                //save it to a Contact object
                Contact contact = new Contact();
                contact.setName(name);
                contact.setMobile(mobile);
                contact.setEmail(email);
                
                //save Contact object to the data structure
                contactsList.add(contact);
                
                //display the new contact within the TextArea 
                area.append(contact.toString());
                area.append("\n");
                
            }
        });
        
        //3.3 permanent storage
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });
        
        //3.4 clear fields
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                area.setText("");
                contactsList.clear();
            }
        });
        
        //3.5 exit btn
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to exit the application?");
                if(i==0) System.exit(0);
            }
        });
        
        //4. Load existing data when the application starts
        loadFromFile();
        
        //5. display existing contacts
        displayContacts();
        
    }
    
    
    //private method to diplay all the contacts from the ArrayList to the TextArea
    private void displayContacts() {
        area.setText("");        
        if (!contactsList.isEmpty()) {                
            for (int i=0; i<contactsList.size(); i++) { 
                area.append(contactsList.get(i).toString());
                area.append("\n");
            }
        } 
    }

    
    //Private method to load contacts from the contacts.txt file to the ArrayList
    private void loadFromFile() {
        try {
            FileReader in = new FileReader("Contacts.txt");//enter your path here
            BufferedReader buff = new BufferedReader(in);
            String line;
            String[] tokens;
            
            while (buff.ready()) {
                line = buff.readLine();
                tokens = line.split("\t");//split() method can split a string to substrings given a specifi delimeter character
                Contact temp = new Contact(tokens[0], tokens[1], tokens[2]);
                contactsList.add(temp);
            }
                        
        } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(this, "Can't find Contacts.txt", "File Access Error\n" +
                ex.getMessage(), JOptionPane.ERROR_MESSAGE); 
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Can't read Contacts.txt", "File Access Error\n" +
                ex.getMessage(), JOptionPane.ERROR_MESSAGE); 
        }
    }
    
    
    //private method to save the contents of the ArrayList to a text file
    private void saveToFile() {
        try {
            FileWriter out = new FileWriter("Contacts.txt");//enter your path here
            BufferedWriter file = new BufferedWriter(out);

            StringBuilder str = new StringBuilder();
            for (int i =0; i<contactsList.size(); i++) {
                str.append(contactsList.get(i).toString());
                str.append("\n");
            }                       
            file.write(str.toString().trim());
            file.close(); 
            JOptionPane.showMessageDialog(saveBtn, "All records saved to Contacts.txt", "Save Completed\n", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(saveBtn, "Can't access Contacts.txt", "File Access Error\n" +
            ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
       
    
    //private method to construct the UI of the application
    private void createUI() {
        exitBtn = new JButton("Exit");
        
        saveBtn = new JButton("Save to file");
        addBtn = new JButton("Add");
        clearBtn = new JButton("Clear");
        
        nameTF = new JTextField("Enter name", 25);
        mobileTF = new JTextField("Enter mobile", 13);
        emailTF = new JTextField("Enter email", 15);
        
        area = new JTextArea();
        
        //create layouts and add components
        this.setLayout(new BorderLayout(5,5));
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        //buttonsPanel.add(loadBtn);
        buttonsPanel.add(saveBtn);
        buttonsPanel.add(clearBtn);        
        buttonsPanel.add(exitBtn);
                      
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        contactPanel.add(nameTF);
        contactPanel.add(mobileTF);
        contactPanel.add(emailTF);
        contactPanel.add(addBtn);
        
        this.add(area, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.add(contactPanel, BorderLayout.NORTH);
    }
       
}
