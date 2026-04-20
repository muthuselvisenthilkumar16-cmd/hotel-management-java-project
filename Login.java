package com.hotel.management;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {

    JTextField username;
    JPasswordField password;
    JButton loginBtn;

    public Login() {
        setTitle("Hotel Management Login");

        username = new JTextField();
        password = new JPasswordField();
        loginBtn = new JButton("Login");

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        userLabel.setBounds(50, 50, 100, 30);
        username.setBounds(150, 50, 150, 30);

        passLabel.setBounds(50, 100, 100, 30);
        password.setBounds(150, 100, 150, 30);

        loginBtn.setBounds(120, 160, 100, 30);

        add(userLabel);
        add(username);
        add(passLabel);
        add(password);
        add(loginBtn);

        // 🔥 LOGIN BUTTON ACTION
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                System.out.println("Login button clicked"); // DEBUG

                String user = username.getText();
                String pass = new String(password.getPassword());

                try {
                    Connection con = DBConnection.getConnection();

                    if (con == null) {
                        JOptionPane.showMessageDialog(null, "Database not connected!");
                        return;
                    }

                    String query = "SELECT * FROM users WHERE username=? AND password=?";
                    PreparedStatement pst = con.prepareStatement(query);

                    pst.setString(1, user);
                    pst.setString(2, pass);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Successful");
                        new Dashboard();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Username or Password");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}