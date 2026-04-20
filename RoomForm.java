package com.hotel.management;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RoomForm extends JFrame {

    JTextField roomNumber, price;
    JComboBox<String> type;
    JButton addBtn;

    public RoomForm() {
        setTitle("Add Room");

        JLabel rLabel = new JLabel("Room No:");
        JLabel tLabel = new JLabel("Type:");
        JLabel pLabel = new JLabel("Price:");

        roomNumber = new JTextField();
        price = new JTextField();

        type = new JComboBox<>(new String[]{"Single", "Double", "Suite"});

        addBtn = new JButton("Add Room");

        rLabel.setBounds(50, 50, 100, 30);
        roomNumber.setBounds(150, 50, 150, 30);

        tLabel.setBounds(50, 100, 100, 30);
        type.setBounds(150, 100, 150, 30);

        pLabel.setBounds(50, 150, 100, 30);
        price.setBounds(150, 150, 150, 30);

        addBtn.setBounds(120, 220, 120, 30);

        add(rLabel);
        add(roomNumber);
        add(tLabel);
        add(type);
        add(pLabel);
        add(price);
        add(addBtn);

        // 🔥 ADD ROOM TO DATABASE
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = DBConnection.getConnection();

                    String roomNo = roomNumber.getText();
                    String roomType = (String) type.getSelectedItem();
                    double roomPrice = Double.parseDouble(price.getText());

                    String query = "INSERT INTO rooms (room_number, type, price, status) VALUES (?, ?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);

                    pst.setString(1, roomNo);
                    pst.setString(2, roomType);
                    pst.setDouble(3, roomPrice);
                    pst.setString(4, "Available");

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Room Added Successfully");

                    roomNumber.setText("");
                    price.setText("");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setSize(400, 350);
        setLayout(null);
        setVisible(true);
    }
}