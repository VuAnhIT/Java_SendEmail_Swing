package com.theanh.ui;


import com.theanh.business.MyMail;
import com.theanh.entity.MailMessage;
import com.theanh.entity.SMTPServer;

import javax.mail.AuthenticationFailedException;
import javax.mail.Session;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

/**
 * Created by Vu The Anh  - vutheanhit@gmail.com
 * On 07/05/2019
 * This is the class that will create GUI, handle button,
 * insert data into instances of SMTPServer
 * and create a instance of MyMail to send email
 */
public class MyEmail implements ActionListener {
    //Declare needed java swing components
    private JTextField txtFrom, txtTo, txtSubject, txtUserName, txtAttachFile;
    private JPasswordField txtPassword;
    private JComboBox<SMTPServer> cbxServer;
    private JTextArea txtMessage;
    private JButton btnAttachFiles, btnClearFiles, btnSend;
    private JPanel pnlTop;
    private JFrame frame;
    private File[] attachFiles = null;

    public void createAndShowGUI() {
        frame = new JFrame("Send E-Mail Client App");

        //Set up the content pane.
        setupComponents();
        setupForm();
        setupData();
        setupButton();

        // Config the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 700));
        frame.setLocationRelativeTo(null);
        frame.pack();

        // Display the JFrame window.
        frame.setVisible(true);
    }

    private void setupComponents() {

        //Instantiate all needed components
        txtFrom = new JTextField(20);
        txtTo = new JTextField(20);
        txtSubject = new JTextField(20);
        cbxServer = new JComboBox<>();
        txtUserName = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtMessage = new JTextArea(20, 20);
        txtAttachFile = new JTextField(20);
        txtAttachFile.setEditable(false);
        btnSend = new JButton("Send E-Mail");
        btnAttachFiles = new JButton("Attach Files");
        btnClearFiles = new JButton("Clear Files");
        pnlTop = new JPanel();
    }

    private void setupForm() {
        /*
         * Set up the top (PAGE_START) of JFrame.
         */

        // Set up top Panel into GridBagLayout
        pnlTop.setLayout(new GridBagLayout());

        //set up the constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(3, 10, 3, 80);
        constraints.anchor = GridBagConstraints.WEST;

        //Create the label/button in the left side first.
        pnlTop.add(new JLabel("From:"), constraints);

        constraints.gridy = 1;
        pnlTop.add(new JLabel("To:"), constraints);

        constraints.gridy = 2;
        pnlTop.add(new JLabel("Subject:"), constraints);

        constraints.gridy = 3;
        pnlTop.add(new JLabel("SMTP Server:"), constraints);

        constraints.gridy = 4;
        pnlTop.add(new JLabel("Username:"), constraints);

        constraints.gridy = 5;
        pnlTop.add(new JLabel("Password:"), constraints);

        constraints.gridy = 6;
        pnlTop.add(btnAttachFiles, constraints);

        constraints.gridy = 7;
        pnlTop.add(btnClearFiles, constraints);

        constraints.gridy = 8;
        pnlTop.add(new JLabel("Message:"), constraints);

        //Create the text arena/box in the right side
        constraints.insets = new Insets(3, 20, 3, 10);

        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlTop.add(txtFrom, constraints);

        constraints.gridy = 1;
        pnlTop.add(txtTo, constraints);

        constraints.gridy = 2;
        pnlTop.add(txtSubject, constraints);

        constraints.gridy = 3;
        cbxServer.setPreferredSize(new Dimension(223, 20)); //set up size of cbx
        pnlTop.add(cbxServer, constraints);

        constraints.gridy = 4;
        pnlTop.add(txtUserName, constraints);

        constraints.gridy = 5;
        pnlTop.add(txtPassword, constraints);

        constraints.gridy = 6;
        pnlTop.add(txtAttachFile, constraints);

        frame.add(pnlTop, BorderLayout.PAGE_START);

        /*
         * Set up the Center of the JFRAME
         */

        // Make sure the text area is word warp
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);
        // Create a scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(txtMessage);
        // Add to frame
        frame.add(scrollPane, BorderLayout.CENTER);

        /*
         * Set up the Bottom of the JFRAME
         */
        //Add send email button
        frame.add(btnSend, BorderLayout.PAGE_END);

    }

    private void setupData() {

        // Setup values of Gmail, Yahoo, Zoho SMTPServer
        SMTPServer gmailSSL = new SMTPServer("SSL", "465", "smtp.gmail.com");
        SMTPServer gmailTLS = new SMTPServer("TLS", "587", "smtp.gmail.com");
        SMTPServer yahooMail = new SMTPServer("SSL", "465", "smtp.mail.yahoo.com");
        SMTPServer zohoMail = new SMTPServer("SSL", "465", "smtp.zoho.com");

        // Add SMTP server to JComboBox cbxServer
        cbxServer.addItem(gmailSSL);
        cbxServer.addItem(gmailTLS);
        cbxServer.addItem(yahooMail);
        cbxServer.addItem(zohoMail);
    }

    private void setupButton() {
        //Add button to action listener
        btnSend.addActionListener(this);
        btnAttachFiles.addActionListener(this);
        btnClearFiles.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //The user click button send email
        if (e.getSource() == btnSend) {
            // Validating all input fields
            if (!validateFields()) {
                return;
            }

            // Create MailMessage object to store all information
            MailMessage mailMessage = new MailMessage(txtTo.getText(),
                    txtFrom.getText(),
                    txtMessage.getText(),
                    txtSubject.getText());

            // Create myMail object to start sending email
            MyMail myMail = new MyMail();
            try {
                // Get mail session
                Session session = myMail.getMailSession((SMTPServer) Objects.requireNonNull(cbxServer.getSelectedItem()),
                        txtUserName.getText(), new String(txtPassword.getPassword()));

                // Start to send email
                boolean success = myMail.sendMail(mailMessage, session, attachFiles);

                // If the email is sent without errors, display this message
                if (success) {
                    JOptionPane.showMessageDialog(frame,
                            "The message successfully sent to " + txtTo.getText(),
                            "Message", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(getClass().getResource("/java-icon.png")));
                }
            } catch (AuthenticationFailedException exAu) {
                // Catch the ex of inputting wrong username or password
                JOptionPane.showMessageDialog(frame,
                        "Your email username or password is incorrect!" +
                                "\nOr you may choose the wrong SMTP Server!" +
                                "\nMore details at: " + exAu.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                // Catch all other ex
                JOptionPane.showMessageDialog(frame,
                        "Error while sending the e-mail!"
                                + "\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }


        } else if (e.getSource() == btnAttachFiles) {
            // The user click button attach files
            // Clear all the old attach Files
            attachFiles = null;
            txtAttachFile.setText("");

            // Open File Chooser
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setMultiSelectionEnabled(true);
            int returnValue = fileChooser.showOpenDialog(null);
            //If the user select files
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Store all the files
                attachFiles = fileChooser.getSelectedFiles();

                // Get all the files address to display in text attach File
                String txtFile = null;
                for (File aFile : attachFiles) {
                    txtFile += '"' + aFile.getAbsolutePath() + '"' + " ";
                    txtAttachFile.setText(txtFile);
                }
            }
        } else if (e.getSource() == btnClearFiles) {
            // The user click button clear files
            // Clear all text address and files
            txtAttachFile.setText("");
            attachFiles = null;
        }
    }

    private boolean validateFields() {
        // Make sure the FROM address is not empty
        if (txtFrom.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Please enter the address 'From'",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtFrom.requestFocus();
            return false;
        }

        // Make sure the TO address is not empty
        if (txtTo.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Please enter the address 'TO'",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtTo.requestFocus();
            return false;
        }

        // Make sure the Subject is not empty
        if (txtSubject.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Please enter the Subject",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtSubject.requestFocus();
            return false;
        }

        // Make sure the Username is not empty
        if (txtUserName.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Please enter the UserName",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtUserName.requestFocus();
            return false;
        }

        // Make sure the Password is not empty
        if (txtPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Please enter the Password",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return false;
        }

        // Make sure the Message is not empty
        if (txtMessage.getText().equals("")) {
            JOptionPane.showMessageDialog(frame, "Please enter the Message",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtMessage.requestFocus();
            return false;
        }
        // If it passed all. The data in the input fields is ready.
        return true;
    }
}
