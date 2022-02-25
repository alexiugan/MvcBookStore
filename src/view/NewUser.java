package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DBConnection;

import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;

public class NewUser extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField userTxt;
    private JTextField passTxt;
    private JTextField roleTxt;
    private JButton cancelBtn, confirmBtn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NewUser frame = new NewUser();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public NewUser() {
        setTitle("Signup Window");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setBounds(100, 100, 356, 327);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel signupLbl = new JLabel("Signup");
        signupLbl.setFont(new Font("Arial", Font.BOLD, 24));
        signupLbl.setBounds(138, 11, 91, 32);
        contentPane.add(signupLbl);

        JSeparator separator = new JSeparator();
        separator.setBounds(56, 53, 255, 2);
        contentPane.add(separator);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(new Font("Arial", Font.BOLD, 18));
        userLbl.setBounds(56, 78, 97, 32);
        contentPane.add(userLbl);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("Arial", Font.BOLD, 18));
        passLbl.setBounds(60, 121, 93, 32);
        contentPane.add(passLbl);

        JLabel roleLbl = new JLabel("Role:");
        roleLbl.setFont(new Font("Arial", Font.BOLD, 18));
        roleLbl.setBounds(106, 164, 47, 32);
        contentPane.add(roleLbl);

        userTxt = new JTextField();
        userTxt.setBounds(173, 82, 97, 28);
        contentPane.add(userTxt);
        userTxt.setColumns(10);

        passTxt = new JTextField();
        passTxt.setColumns(10);
        passTxt.setBounds(173, 125, 97, 28);
        contentPane.add(passTxt);

        roleTxt = new JTextField();
        roleTxt.setColumns(10);
        roleTxt.setBounds(173, 168, 97, 28);
        contentPane.add(roleTxt);

        confirmBtn = new JButton("Confirm");
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 12));
        confirmBtn.setBounds(56, 234, 103, 32);
        confirmBtn.addActionListener(this);
        contentPane.add(confirmBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 12));
        cancelBtn.setBounds(185, 234, 103, 32);
        cancelBtn.addActionListener(this);
        contentPane.add(cancelBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==cancelBtn)
            dispose();
        else if(e.getSource()==confirmBtn)
        {
            addUser();
        }
    }

    private void addUser()
    {
        int role=-1;
        String user ="", pass="";
        try {
            role = Integer.parseInt(roleTxt.getText());
            user = userTxt.getText();
            pass = passTxt.getText();
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input!", "Input error!", JOptionPane.ERROR_MESSAGE);
        }
        if(roleTxt.getText().equals("") || userTxt.getText().equals("") || passTxt.getText().equals(""))
            JOptionPane.showMessageDialog(this, "Empty fields!", "Input error!", JOptionPane.ERROR_MESSAGE);
        else if(userExists(userTxt.getText()))
            JOptionPane.showMessageDialog(this, "User Exists!", "User Exists!", JOptionPane.ERROR_MESSAGE);
        else
        {
            try {
                DBConnection db = DBConnection.getConnection();
                Connection con = db.connection;

                String sql = "INSERT INTO users (role, username, password) VALUES (?, ?, ?)";
                PreparedStatement prepSt = con.prepareStatement(sql);
                prepSt.setInt (1, role);
                prepSt.setString(2, user);
                prepSt.setString(3, pass);

                prepSt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Account created successfuly!");
                dispose();
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Database error!", "DB error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public boolean userExists(String username)
    {
        try {
            DBConnection db = DBConnection.getConnection();
            Connection con = db.connection;

            String sql = "SELECT * FROM users WHERE username= ?";
            PreparedStatement prepSt = con.prepareStatement(sql);
            prepSt.setString (1, username);
            ResultSet rs = prepSt.executeQuery();

            if(rs.next())
                return true;
            return false;
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(this, "DB error when checking user", "DB errror!", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }
}
