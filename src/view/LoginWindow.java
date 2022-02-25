package view;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.AdminController;
import controller.EmpController;
import model.BookDeposit;
import model.DBConnection;
import model.User;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.ButtonGroup;

public class LoginWindow {

    BookDeposit b;
    JButton btnLogin;
    public JRadioButton rbtnEmp, rbtnAdmin;
    public JFrame frame;
    public JTextField txtUsername;
    private JPasswordField txtPassword;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        //	LoginWindow window = new LoginWindow(new User());


    }

    /**
     * Create the application.
     */
    public LoginWindow(BookDeposit b) {
        initialize(b);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(BookDeposit b) {
        this.b=b;
        frame = new JFrame("Login Window");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblLogin.setBounds(184, 11, 98, 35);
        frame.getContentPane().add(lblLogin);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblUsername.setBounds(76, 77, 85, 35);
        frame.getContentPane().add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPassword.setBounds(76, 123, 85, 14);
        frame.getContentPane().add(lblPassword);

        rbtnAdmin = new JRadioButton("Administrator");
        buttonGroup.add(rbtnAdmin);
        rbtnAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rbtnAdmin.setBounds(200, 180, 123, 23);
        frame.getContentPane().add(rbtnAdmin);

        rbtnEmp = new JRadioButton("Employee");
        buttonGroup.add(rbtnEmp);
        rbtnEmp.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rbtnEmp.setBounds(96, 180, 95, 23);
        rbtnEmp.setSelected(true);
        frame.getContentPane().add(rbtnEmp);

        txtUsername = new JTextField();
        txtUsername.setBounds(171, 86, 139, 20);
        frame.getContentPane().add(txtUsername);
        txtUsername.setColumns(10);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(67, 228, 89, 23);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                boolean correct = checkCredentials();
                if(correct)
                {
                    if(rbtnEmp.isSelected())
                        new EmpGUI(txtUsername.getText(), new BookDeposit());
                    else if(rbtnAdmin.isSelected())
                    {
                        BookDeposit b = new BookDeposit();
                        new AdminController(new AdminGUI(txtUsername.getText(), b), b);
                    }
                }
            }

        });
        frame.getContentPane().add(btnLogin);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        btnCancel.setBounds(166, 228, 89, 23);
        frame.getContentPane().add(btnCancel);

        JButton signup = new JButton("Signup");
        signup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new NewUser();
            }
        });
        signup.setBounds(265, 228, 89, 23);
        frame.getContentPane().add(signup);

        JSeparator separator = new JSeparator();
        separator.setBounds(52, 214, 314, 3);
        frame.getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(52, 63, 314, 3);
        frame.getContentPane().add(separator_1);

        JCheckBox cboxPassword = new JCheckBox("Show Password");
        cboxPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(cboxPassword.isSelected())
                    txtPassword.setEchoChar((char)0);
                else
                    txtPassword.setEchoChar('*');
            }
        });

        cboxPassword.setBounds(135, 150, 145, 24);
        frame.getContentPane().add(cboxPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(171, 121, 139, 22);
        frame.getContentPane().add(txtPassword);

        frame.setVisible(true);
    }

    public User checkUser(String user) {
        User u = new User(-1, user, "");
        try {
            DBConnection db = DBConnection.getConnection();
            Connection con = db.connection;

            String sql = "SELECT role, username, password FROM users WHERE username = ?";
            PreparedStatement prepSt = con.prepareStatement(sql);
            prepSt.setString (1, user);

            ResultSet rs = prepSt.executeQuery();
            rs.next();
            String password = rs.getString("password");
            int role = rs.getInt("role");
            rs.close();

            u.setRole(role);
            u.setPassword(password);

        }
        catch(Exception e) {
            System.out.println("User not found");
            //JOptionPane.showMessageDialog(frame, "Database error!", "DB error!", JOptionPane.ERROR_MESSAGE);
        }
        return u;
    }

    public void addLoginListener(ActionListener a) {
        btnLogin.addActionListener(a);
    }

    public boolean checkCredentials() {
        boolean correct = true;
        User u = checkUser(txtUsername.getText());
        if(txtPassword.getText().equals(u.getPassword()))
        {
            if(rbtnEmp.isSelected() && u.getRole()==0)
            {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                EmpGUI emp = new EmpGUI(u.getUsername(), b);
                EmpController empCtrl = new EmpController(emp, b);
                emp.setVisible(true);
            }

            else if(rbtnAdmin.isSelected() && u.getRole()==1)
            {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                AdminGUI adm= new AdminGUI(u.getUsername(), b);
                AdminController adminCtrl= new AdminController(adm,b);
                adm.setVisible(true);

            }

            else
            {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Login Error", JOptionPane.ERROR_MESSAGE);
                correct = false;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Login Error", JOptionPane.ERROR_MESSAGE);
            correct = false;
        }
        return correct;
    }
}
